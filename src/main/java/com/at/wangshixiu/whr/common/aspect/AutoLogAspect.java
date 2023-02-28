package com.at.wangshixiu.whr.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.at.wangshixiu.whr.entity.sys.SysLog;
import com.at.wangshixiu.whr.service.ISysLogService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


/**
 * @Author 王世秀
 * @Date 2022/12/20
 * @Description 记录日志
 */
@Slf4j
@Aspect
@Component
public class AutoLogAspect {
    @Resource
    private ISysLogService iSysLogService;

    @Pointcut("@annotation(com.at.wangshixiu.whr.common.interfaces.AutoLog)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        saveSysLog(method, time, result);
        //保存日志
        if (6000 > time) {
            String methodName = method.getName();
            String className = method.getDeclaringClass().getName();
            log.warn("{}类中的{}方法执行比较费时,所用时间:{}", className, methodName, time);
        }
        return result;
    }

    private void saveSysLog(Method method, long time, Object result) {
        SysLog sysLog =
                getSysLog(method, time, result);
        iSysLogService.save(sysLog);
    }

    public SysLog getSysLog(Method method, Long time, Object result) {
        String className = method.getClass().getName();
        String methodName = method.getName();
        return SysLog.builder()
                .description(method.getAnnotation(ApiOperation.class).value())
                .className(className)
                .methodName(methodName)
                .args(JSONObject.toJSON(method.getParameters()).toString())
                .operationType(getOperationType(method))
                .returnData(JSONObject.toJSON(result).toString())
                .millisecond(time)
                .createTime(new Date())
                .createBy("张三")
                .build();
    }

    public String getOperationType(Method method) {
        if (method.isAnnotationPresent(GetMapping.class)) {
            String[] value = method.getAnnotation(GetMapping.class).value();
            if ("login".equals(value[0])) {
                return "登录";
            }
            return "查询";
        }
//       TODO 上传和下载文件还要处理
        if (method.isAnnotationPresent(PostMapping.class)) {
            return "添加";
        }
        if (method.isAnnotationPresent(PutMapping.class)) {
            return "更新";
        }

        return "删除";
    }
}
