package com.example.srb.core.controller.admin;


import com.example.srb.core.pojo.entity.IntegralGrade;
import com.example.srb.core.service.IntegralGradeService;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/admin/core/integralGrade")
public class AdminIntegralGradeController {

    @Resource
    private IntegralGradeService integralGradeService;

    @GetMapping("/list")
    public List<IntegralGrade> listAll(){
        List<IntegralGrade> integralGradeList = integralGradeService.list();
        integralGradeList.forEach(System.out::println);
        return integralGradeList;
    }

    @DeleteMapping("/remove/{id}")
    public boolean removeById(@PathVariable int id){
        return integralGradeService.removeById(id);
    }
}

