package com.example.srb.common.exception;

import com.example.srb.common.result.ResponseEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常类
 * @NoArgsConstructor的作用是防止有参构造函数使得无参构造函数失效
 * @author fangzheng
 * @date 2022/04/25/15/11
 */
@Data
@NoArgsConstructor
public class BusinessException extends RuntimeException{

    private Integer code;

    private String message;

    public BusinessException(String message){
        this.message = message;
    }

    public BusinessException(String message, Integer code){
        this.message = message;
        this.code = code;
    }

    public  BusinessException(String message, Integer code, Throwable cause){
        super(cause);
        this.message = message;
        this.code = code;
    }

    public BusinessException(ResponseEnum responseEnum){
        this.message = responseEnum.getMsg();
        this.code = responseEnum.getCode();
    }

    public BusinessException(ResponseEnum responseEnum, Throwable cause){
        super(cause);
        this.message = responseEnum.getMsg();
        this.code = responseEnum.getCode();
    }
}
