package com.example.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.srb.core.listener.ExcelDictDTOListener;
import com.example.srb.core.mapper.DictMapper;
import com.example.srb.core.pojo.entity.Dict;
import com.example.srb.core.pojo.entity.dto.ExcelDictDTO;
import com.example.srb.core.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public List<Dict> listByParentId(Long parentId) {

        List<Dict> dicts = new ArrayList<>();
        try{
            dicts = (List<Dict>) redisTemplate.opsForValue().get("srb:core:dictList:" + parentId);
            if(dicts != null){
                log.info("从redis取数据！");
                return dicts;
            }
        }catch (Exception e){
            log.error("redis服务器异常： " + ExceptionUtils.getStackTrace(e));
        }

        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        dicts = dictMapper.selectList(wrapper);
        dicts.forEach(dict -> {
            dict.setHasChildren(hasChildren(dict.getId()));
        });
        try{
            //TODO redis数据缓存时间设置
            redisTemplate.opsForValue().set("srb:core:dictList:" + parentId, dicts, 5, TimeUnit.MINUTES);
            log.info("数据加入Redis缓存！");
        }catch (Exception e){
            log.error("redis服务器异常： " + ExceptionUtils.getStackTrace(e));
        }
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
