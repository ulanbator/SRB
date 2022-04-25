package com.example.srb.common.exception;

import com.example.srb.common.result.R;
import com.example.srb.common.result.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author fangzheng
 * @date 2022/04/25/10/31
 * 统一异常处理类
 */
@Slf4j
@Component
@RestControllerAdvice
public class UnifiedExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public R handleException(Exception e){
        log.error(e.getMessage(), e);
        return R.error();
    }

    @ExceptionHandler(value = BadSqlGrammarException.class)
    public R handleSQLException(Exception e){
        log.error(e.getMessage(), e);
        return R.setResult(ResponseEnum.BAD_SQL_GRAMMAR_ERROR);
    }
}
