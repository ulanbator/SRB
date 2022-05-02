package com.example.srb.core.pojo.entity.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author fangzheng
 * @date 2022/05/01/20/48
 */
@Data
public class ExcelDictDTO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("上级id")
    private Long parentId;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("值")
    private String value;

    @ExcelProperty("编码")
    private String dictCode;
}
