package com.at.wangshixiu.whr.service.impl;

import com.at.wangshixiu.whr.common.Result;
import com.at.wangshixiu.whr.common.util.MyStringUtils;
import com.at.wangshixiu.whr.dto.LoginUserDto;
import com.at.wangshixiu.whr.entity.SysUser;
import com.at.wangshixiu.whr.mapper.SysUserMapper;
import com.at.wangshixiu.whr.service.ISysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author 王世秀
 * @Date 2022/12/20
 * @Description
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    private static final String forbiddenState = "0";
    private static final String lockState = "2";
    private static final Integer errorMaxNumber = 3;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public Result<?> handleLogin(LoginUserDto loginUserDto, HttpServletRequest request) {
        //1、判断验证码是否真确
        String code = (String) redisTemplate.opsForValue().get(loginUserDto.getKey());
        if (Objects.isNull(code)) {
            return Result.error("验证码已过期");
        }
        if (!Objects.equals(code, loginUserDto.getCode())) {
            return Result.error("验证码错误");
        }

        //2、判断用户状态是否正常  (登录错误次数不超过3次  无禁用  IP是否正常)
        // 2.1 禁用处理
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getUsername, loginUserDto.getUserName());
        lambdaQueryWrapper.eq(SysUser::getPassword, loginUserDto.getPassword());
        SysUser sysUser = super.getOne(lambdaQueryWrapper);
        if (null == sysUser) {
            //设置该账号登录的错误次数
            redisTemplate.opsForValue().increment(loginUserDto.getUserName());
            return Result.error("用户名或密码错误");
        }else {
            Integer errorNumber = (Integer) redisTemplate.opsForValue().get(loginUserDto.getUserName());
            //判断是否超过了最大错误值
            if (null != errorNumber && errorMaxNumber < errorNumber) {
                String userIP = getUserIP(request);
                if (!Objects.equals(userIP, sysUser.getIp())) {
                    String userName = loginUserDto.getUserName();
                    LambdaUpdateWrapper<SysUser> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                    lambdaUpdateWrapper.set(MyStringUtils.isNotNull(loginUserDto.getUserName()), SysUser::getState, lockState);
                    lambdaUpdateWrapper.eq(SysUser::getUsername, userName);
                    //把该账号的状态改为锁定锁定状态
                    String state = sysUser.getState();
                    if (!Objects.equals(lockState, state)) {
                        super.update(lambdaUpdateWrapper);
                        log.info("用户:{}的账号已被修改为锁定状态",sysUser.getUsername());
                    }
                    //删除redis中的key
                    redisTemplate.delete(loginUserDto.getUserName());
                    //   TODO  异步发送消息给用户
                    //尊敬的xx(先生/女士) 您的xxx账号 在xx时间 于xx地方登录 且登录错误次数超过了3次 如非本人操作请立刻修改密码
                    log.warn("用户:{}的IP与上次登录的不一致", sysUser.getUsername());
                    return Result.error("账户已被锁定,请24小时后重新登录");
                }
            }
        }
        if (Objects.equals(forbiddenState, sysUser.getState())) {
            return Result.error("账户已被禁用,请联系管理员解除禁用");
        }
        return Result.ok("登录成功");
    }

    public String getUserIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }
}
