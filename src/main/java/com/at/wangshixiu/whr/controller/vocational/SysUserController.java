package com.at.wangshixiu.whr.controller.vocational;

import com.at.wangshixiu.whr.common.Result;
import com.at.wangshixiu.whr.common.util.MyStringUtils;
import com.at.wangshixiu.whr.common.validation.GroupsClass;
import com.at.wangshixiu.whr.controller.BaseController;
import com.at.wangshixiu.whr.entity.sys.SysUser;
import com.at.wangshixiu.whr.service.ISysUserService;

import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author 王世秀
 * @Date 2022/12/20
 * @Description
 */
@Api(tags = "用户对象功能接口")
@Slf4j
@Validated
@RestController
@RequestMapping("/sysUser")
public class SysUserController extends BaseController<ISysUserService> {

    @ApiOperation("分页列表查询-用户")
    @GetMapping("/pageQuery")
    public Result<?> pageQuery(SysUser sysUser, PageRequest pageRequest, HttpServletRequest request) {
        return super.queryPageList(sysUser, pageRequest, request);
    }

    @ApiOperation("通过ID查询单条数据")
    @GetMapping("/queryById")
    public Result<?> queryById(String id) {
        return findById(id);
    }

    @ApiOperation("新增用户")
    @PostMapping("/save")
    public Result<?> save(@Validated(GroupsClass.InsertClass.class) @RequestBody SysUser sysUser) {
       return baseService.handleSaveUser(sysUser);
    }

    @ApiOperation("修改用户")
    @PutMapping("/edit")
    public Result<?> edit(@Validated(GroupsClass.UpdateClass.class) @RequestBody SysUser sysUser){
        return updateById(sysUser);
    }

    @ApiOperation("通过主键删除数据")
    @DeleteMapping("/delete")
    public Result<?> delete(String id) {
        return removeById(id);
    }

    @ApiOperation("获取用户的权限信息")
    @DeleteMapping("/getUserDataPermissions")
    public Result<?> getUserDataPermissions(){
       return baseService.getUserDataPermissions();
    }
}
