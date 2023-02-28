package com.at.wangshixiu.whr.service.impl;

import com.at.wangshixiu.whr.common.Result;
import com.at.wangshixiu.whr.common.util.CommonConstant;
import com.at.wangshixiu.whr.dto.LoginUserDto;
import com.at.wangshixiu.whr.dto.MenuDto;
import com.at.wangshixiu.whr.entity.sys.SysUser;
import com.at.wangshixiu.whr.mapper.SysMenuMapper;
import com.at.wangshixiu.whr.mapper.SysUserMapper;
import com.at.wangshixiu.whr.service.ISysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author 王世秀
 * @Date 2022/12/20
 * @Description
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public Result<?> handleLogin(LoginUserDto loginUserDto, HttpServletRequest request) {
        //1、判断验证码是否真确
        String code = (String) redisTemplate.opsForValue().get(loginUserDto.getKey());
        if (Objects.isNull(code)) {
            return Result.error(CommonConstant.Login.VERIFICATION_CODE_EXPIRE);
        }
        if (!Objects.equals(code, loginUserDto.getCode())) {
            return Result.error(CommonConstant.Login.VERIFICATION_CODE_ERROR);
        }

        //2、判断用户状态是否被禁用
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getUsername, loginUserDto.getUserName());
        lambdaQueryWrapper.eq(SysUser::getPassword, loginUserDto.getPassword());
        SysUser sysUser = super.getOne(lambdaQueryWrapper);
        if (null == sysUser) {
            return Result.error(CommonConstant.User.USERNAME_PASSWORD_ERROR);
        }
        if (Objects.equals(CommonConstant.User.FORBIDDEN_STATE, sysUser.getState())) {
            return Result.error(CommonConstant.User.USER_IS_NOT_AVAILABLE);
        }
        // SoToken保存信息
        StpUtil.login(sysUser.getId());
        final SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return Result.ok(tokenInfo, CommonConstant.Login.LOGIN_SUCCESS);
    }

    @Override
    public Result<?> getUserDataPermissions() {
        //获取当前登录用户的id
        final String loginId = (String) StpUtil.getLoginId();
        //根据用户查询其拥有的菜单 及 按钮权限
        List<MenuDto> menuDtoList =  sysMenuMapper.getUserMenu(loginId);
        return Result.ok();
    }

    @Override
    public Result<?> handleSaveUser(SysUser sysUser) {
        // 添加的前提是用户不存在
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getUsername, sysUser.getUsername())
                .eq(SysUser::getPassword, sysUser.getPassword())
                .eq(SysUser::getPhone, sysUser.getPhone())
                .eq(SysUser::getEmail, sysUser.getEmail());
        if (baseMapper.selectCount(lambdaQueryWrapper) > 0) return Result.error(CommonConstant.User.USER_IS_NOT_EXIST);
        return save(sysUser) ? Result.ok(CommonConstant.User.USER_SAVE_SUCCESS) : Result.error(CommonConstant.User.USER_SAVE_ERROR);
    }
}
