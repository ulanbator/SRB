package com.example.srb.core.controller.admin;


import com.example.srb.common.exception.Assert;
import com.example.srb.common.result.R;
import com.example.srb.common.result.ResponseEnum;
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
    public R listAll(){
        List<IntegralGrade> integralGradeList = integralGradeService.list();
        return R.ok().data("list", integralGradeList).message("获取列表成功");
    }

    @DeleteMapping("/remove/{id}")
    public R removeById(@PathVariable int id){
        boolean flag = integralGradeService.removeById(id);
        if(flag){
            return R.ok().message("删除成功");
        }else{
            return R.error().message("删除失败");
        }
    }

    @PostMapping("/save")
    public R save(@RequestBody IntegralGrade integralGrade){
       boolean flag = integralGradeService.save(integralGrade);
       Assert.notNull(integralGrade.getBorrowAmount(), ResponseEnum.BORROW_AMOUNT_NULL_ERROR);
       if(flag){
           return R.ok().data("data",integralGrade).message("添加成功");
       }else{
           return R.error().message("添加失败");
       }
    }

    @GetMapping("/get/{id}")
    public R getById(@PathVariable long id){
        IntegralGrade integralGrade =  integralGradeService.getById(id);
        if(integralGrade != null){
            return R.ok().data("data",integralGrade).message("查询成功");
        }else{
            return R.error().message("查询失败");
        }
    }

    @PutMapping("/update")
    public R updateById(@RequestBody IntegralGrade integralGrade){
        boolean flag = integralGradeService.updateById(integralGrade);
        if(flag){
            return R.ok().data("data", integralGrade).message("修改成功");
        }else{
            return R.error().message("修改失败");
        }
    }
}

