package com.at.wangshixiu.whr.service;

import com.at.wangshixiu.whr.common.Result;
import com.at.wangshixiu.whr.dto.LoginUserDto;
import com.at.wangshixiu.whr.entity.sys.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 王世秀
 * @Date 2022/12/20
 * @Description 用户表表(sys_user)服务接口
 */
public interface ISysUserService extends IService<SysUser> {
    /**
     * 处理用户登录
     * @param loginUserDto  封装的登录对象
     * @param request
     * @return
     */
    Result<?> handleLogin(LoginUserDto loginUserDto, HttpServletRequest request);

    /**
     * 获取登录用户的权限信息
     * @return
     */
    Result<?> getUserDataPermissions();

    /**
     * 处理用户是否存在
     * @param sysUser
     * @return
     */
    Result handleSaveUser(SysUser sysUser);
}
