package com.at.wangshixiu.whr.service.impl;

import com.at.wangshixiu.whr.common.Result;
import com.at.wangshixiu.whr.common.util.MyStringUtils;
import com.at.wangshixiu.whr.dto.LoginUserDto;
import com.at.wangshixiu.whr.entity.sys.SysUser;
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

        //2、判断用户状态是否正常
        // 2.1 禁用处理
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getUsername, loginUserDto.getUserName());
        lambdaQueryWrapper.eq(SysUser::getPassword, loginUserDto.getPassword());
        SysUser sysUser = super.getOne(lambdaQueryWrapper);
        if (null == sysUser) {
            return Result.error("用户名或密码错误");
        }
        if (Objects.equals(forbiddenState, sysUser.getState())) {
            return Result.error("账户已被禁用,请联系管理员解除禁用");
        }
        return Result.ok("登录成功");
    }
}
