package com.at.wangshixiu.whr.common.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author 王世秀
 * @Date 2023/2/16
 * @Description 数据过滤
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DatePermission {
    /* 规则 */

}
