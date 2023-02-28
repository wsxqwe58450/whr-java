package com.at.wangshixiu.whr.common.interfaces;

import com.at.wangshixiu.whr.common.sensitive.SensitiveTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author 王世秀
 * @Date 2022/12/20
 * @Description 数据脱敏注解
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Desensitized {
    /**
     * 脱敏类型(规则)
     */
    SensitiveTypeEnum type();
}
