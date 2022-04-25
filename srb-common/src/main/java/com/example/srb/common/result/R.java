package com.example.srb.common.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fangzheng
 * @date 2022/04/24/19/37
 */
@Data
public class R {

    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<>();


    private R(){

    }

    public static R ok(){
        R r = new R();
        r.setCode(ResponseEnum.SUCESS.getCode());
        r.setMessage(ResponseEnum.SUCESS.getMsg());
        return r;
    }

    public static R error(){
        R r = new R();
        r.setCode(ResponseEnum.ERROR.getCode());
        r.setMessage(ResponseEnum.ERROR.getMsg());
        return r;
    }

    /**
     * 构造特定返回结果
     * @param responseEnum
     * @return
     */
    public static R setResult(ResponseEnum responseEnum){
        R r = new R();
        r.setCode(responseEnum.getCode());
        r.setMessage(responseEnum.getMsg());
        return r;
    }

    public R data(String key, Object object){
        this.data.put(key, object);
        return this;
    }

    public R data(Map<String, Object> map){
        this.data = map;
        return this;
    }

    public R message(String message){
        this.message = message;
        return this;
    }

    public R code(Integer code){
        this.code = code;
        return this;
    }
}
