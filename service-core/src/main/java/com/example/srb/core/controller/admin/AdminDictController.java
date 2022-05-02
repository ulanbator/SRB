package com.example.srb.core.controller.admin;

import com.example.srb.common.exception.BusinessException;
import com.example.srb.common.result.R;
import com.example.srb.common.result.ResponseEnum;
import com.example.srb.core.service.DictService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;

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
}
