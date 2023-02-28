package com.at.wangshixiu.whr.controller.vocational;

import com.at.wangshixiu.whr.common.Result;
import com.at.wangshixiu.whr.common.util.MyStringUtils;
import com.at.wangshixiu.whr.controller.BaseController;
import com.at.wangshixiu.whr.entity.sys.SysUser;
import com.at.wangshixiu.whr.service.ISysUserService;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author 王世秀
 * @Date 2022/12/20
 * @Description
 */
@Api(tags = "sys_user对象功能接口")
@Slf4j
@RestController
@RequestMapping("/sysUser")
public class SysUserController extends BaseController<ISysUserService> {

    @ApiOperation("分页列表查询-用户")
    @GetMapping("/paginationQuery")
    public Result<?> paginationQuery(SysUser sysUser, PageRequest pageRequest, HttpServletRequest request) {
        return super.queryPageList(sysUser, pageRequest, request);
    }

    @ApiOperation("通过ID查询单条数据")
    @GetMapping("/queryById")
    public Result<?> queryById(String id) {
        return super.findById(id);
    }

    @ApiOperation("新增或更新-用户,有id更新,无id添加")
    @RequestMapping(value = "/saveOrRevise", method = {RequestMethod.POST, RequestMethod.PUT})
    public Result<?> saveSysUser(@RequestBody SysUser sysUser) {
        return MyStringUtils.isNotNull(sysUser.getId()) ? super.updateById(sysUser) : super.save(sysUser);
    }

    @ApiOperation("通过主键删除数据")
    @DeleteMapping("/deleteById")
    public Result<?> deleteById(String id) {
        return super.removeById(id);
    }

}
