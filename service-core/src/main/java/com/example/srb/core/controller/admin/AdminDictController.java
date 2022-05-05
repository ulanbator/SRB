package com.example.srb.core.controller.admin;

import com.alibaba.excel.EasyExcel;
import com.example.srb.common.exception.BusinessException;
import com.example.srb.common.result.R;
import com.example.srb.common.result.ResponseEnum;
import com.example.srb.core.pojo.entity.Dict;
import com.example.srb.core.pojo.entity.dto.ExcelDictDTO;
import com.example.srb.core.service.DictService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author fangzheng
 * @date 2022/05/01/21/04
 */
@CrossOrigin
@RestController
@RequestMapping("admin/core/dict")
public class AdminDictController {

    @Resource
    private DictService dictService;

    @PostMapping("/import")
    public R importData(@RequestParam("file") MultipartFile file){
        try {
            InputStream inputStream = file.getInputStream();
            dictService.importData(inputStream);
            return R.ok().message("批量导入成功");
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, e);
        }
    }

    @GetMapping("/export")
    public void exportData(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("字典数据字典", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), ExcelDictDTO.class).autoCloseStream(Boolean.FALSE).sheet("模板")
                    .doWrite(dictService.listDictData());
        } catch (IOException e) {
            throw new BusinessException(ResponseEnum.EXPORT_DATA_ERROR, e);
        }
    }

    @GetMapping("/listByParentId/{id}")
    public R listByParentId(@PathVariable("id") long id){
        List<Dict> list = dictService.listByParentId(id);
        if(list != null){
            return R.ok().message("查询成功").data("data", list);
        }else{
            return R.error().message("查询失败");
        }
    }
}
