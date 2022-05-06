package com.example.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.srb.core.listener.ExcelDictDTOListener;
import com.example.srb.core.mapper.DictMapper;
import com.example.srb.core.pojo.entity.Dict;
import com.example.srb.core.pojo.entity.dto.ExcelDictDTO;
import com.example.srb.core.service.DictService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author fangzheng
 * @since 2022-04-20
 */
@Service
@Slf4j
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {


    @Resource
    private DictMapper dictMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void importData(InputStream inputStream) {
        EasyExcel.read(inputStream, ExcelDictDTO.class, new ExcelDictDTOListener(dictMapper)).sheet().doRead();
    }

    @Override
    public List<ExcelDictDTO> listDictData() {
        List<Dict> dicts = dictMapper.selectList(null);
        List<ExcelDictDTO> dictDTOS = new ArrayList<>();
        for(Dict dict: dicts){
            ExcelDictDTO excelDictDTO = new ExcelDictDTO();
            BeanUtils.copyProperties(dict, excelDictDTO);
            dictDTOS.add(excelDictDTO);
        }
        dicts.forEach(dict -> {
            ExcelDictDTO excelDictDTO = new ExcelDictDTO();
            BeanUtils.copyProperties(dict, excelDictDTO);
            dictDTOS.add(excelDictDTO);
        });
        return dictDTOS;
    }

    @Override
    @Cacheable(cacheNames = "dicts", key = "#parentId")
    public List<Dict> listByParentId(Long parentId) {
        ObjectMapper objectMapper = new ObjectMapper();
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        List<Dict> dicts = dictMapper.selectList(wrapper);
        dicts.forEach(dict -> {
            dict.setHasChildren(hasChildren(dict.getId()));
        });
        log.info("数据库数据查询！");
        return dicts;
    }

    /**
     *
     * @param parentId 查询的父节点ID
     * @return 该父节点下是否有子节点
     */
    private boolean hasChildren(Long parentId){
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        Integer count = dictMapper.selectCount(wrapper);
        if(count > 0){
            return true;
        }else{
            return false;
        }
    }
}
