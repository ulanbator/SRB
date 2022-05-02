package com.example.srb.core.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.srb.core.mapper.DictMapper;
import com.example.srb.core.pojo.entity.dto.ExcelDictDTO;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fangzheng
 * @date 2022/05/01/20/52
 * 以@Resource引入dictMapper前提Listener也需要被容器管理
 * 所以可以在controller层通过依赖注入，然后在listener使用有参函数使用
 * 批量插入的时候需要考虑不满足BATCH_NUM的数据如何处理
 */
@Slf4j
@NoArgsConstructor
public class ExcelDictDTOListener extends AnalysisEventListener<ExcelDictDTO> {

    private List<ExcelDictDTO> excelDictDTOList = new ArrayList<>();

    private DictMapper dictMapper;

    private static int BATCH_NUM = 5;

    public  ExcelDictDTOListener(DictMapper mapper){
        this.dictMapper = mapper;
    }

    @Override
    public void invoke(ExcelDictDTO excelDictDTO, AnalysisContext analysisContext) {
        excelDictDTOList.add(excelDictDTO);
        if(excelDictDTOList.size() >= BATCH_NUM){
            batchSave();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if(excelDictDTOList.size() > 0){
            batchSave();
        }
        log.info("所有数据解析完成！");
    }


    private void batchSave(){
        dictMapper.batchInsert(excelDictDTOList);
        log.info("批量插入{}条数据", excelDictDTOList.size());
        excelDictDTOList.clear();
    }
}
