package com.at.wangshixiu.whr.common.interfaces;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import lombok.AllArgsConstructor;


/**
 * @Author 王世秀
 * @Date 2022/12/20
 * @Description 用于记录用户的操作日志
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoLog {
    //    日志描述 配合ApiOperation  的value使用



    @AllArgsConstructor
    public enum LogTypeEnum {
        LOGIN(1,"登录"),
        SELECT(2,"查询"),
        HETERO(3,"其他");
        private Integer typeId;
        private String typeValue;
    }
}
