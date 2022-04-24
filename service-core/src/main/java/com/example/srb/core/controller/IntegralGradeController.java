package com.example.srb.core.controller;


import com.example.srb.core.pojo.entity.IntegralGrade;
import com.example.srb.core.service.IntegralGradeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 积分等级表 前端控制器
 * </p>
 *
 * @author fangzheng
 * @since 2022-04-20
 */
@CrossOrigin
@RestController
@RequestMapping("/integralGrade")
public class IntegralGradeController {

    @Resource
    private IntegralGradeService integralGradeService;

    public List<IntegralGrade> listAll(){
        return integralGradeService.list();
    }
}

