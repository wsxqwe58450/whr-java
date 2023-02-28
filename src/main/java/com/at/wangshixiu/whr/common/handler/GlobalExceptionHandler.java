package com.at.wangshixiu.whr.common.handler;

import com.at.wangshixiu.whr.common.Result;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @Author 王世秀
 * @Date 2022/12/20
 * @Description
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public Result bindExceptionHandler(final BindException bindException) {
        List<FieldError> fieldErrors = bindException.getBindingResult().getFieldErrors();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < fieldErrors.size(); i++) {
            if (i < fieldErrors.size() - 1) {
                stringBuilder.append(fieldErrors.get(i).getDefaultMessage()).append(",");
            } else {
                stringBuilder.append(fieldErrors.get(i).getDefaultMessage());
            }
        }
        return Result.error(400, stringBuilder.toString());
    }
}

