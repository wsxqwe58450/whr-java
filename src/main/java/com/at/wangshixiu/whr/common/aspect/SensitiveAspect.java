package com.at.wangshixiu.whr.common.aspect;

import com.at.wangshixiu.whr.common.util.DesensitizedUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Author 王世秀
 * @Date 2022/12/20
 * @Description 数据脱敏
 */
@Aspect
@Component
public class SensitiveAspect {
    public static final String ACCESS_EXECUTION = "execution(* com.at.wangshixiu.whrjava.controller.vocational..*.*(..))";

    /**
     * 注解脱敏处理
     */
    @Around(ACCESS_EXECUTION)
    public Object sensitiveClass(ProceedingJoinPoint joinPoint) throws Throwable {
        return sensitiveFormat(joinPoint);
    }

    /**
     * 注解统一拦截器
     */
    public Object sensitiveFormat(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj = joinPoint.proceed();
        if (obj == null || isPrimitive(obj.getClass())) {
            return obj;
        }

        if (DesensitizedUtil.DESENT_STATUS.equals("1")) {
            DesensitizedUtil.desentData(obj);
        }
        return obj;
    }

    /**
     * 基本数据类型和String类型判断
     */
    public static boolean isPrimitive(Class<?> clz) {
        try {
            if (String.class.isAssignableFrom(clz) || clz.isPrimitive()) {
                return true;
            } else {
                return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
            }
        } catch (Exception e) {
            return false;
        }
    }

}
