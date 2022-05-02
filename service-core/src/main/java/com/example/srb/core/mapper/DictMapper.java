package com.example.srb.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.srb.core.pojo.entity.Dict;
import com.example.srb.core.pojo.entity.dto.ExcelDictDTO;

import java.util.List;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author fangzheng
 * @since 2022-04-20
 */
public interface DictMapper extends BaseMapper<Dict> {
    /**
     *
     * @param list 字典Excel实体列表
     * 实现批量插入字典
     */
    public void batchInsert(List<ExcelDictDTO> list);
}
