package com.at.wangshixiu.whr.controller.vocational;

import com.at.wangshixiu.whr.common.Result;
import com.at.wangshixiu.whr.common.validation.GroupsClass;
import com.at.wangshixiu.whr.controller.BaseController;
import com.at.wangshixiu.whr.entity.sys.SysDept;
import com.at.wangshixiu.whr.service.ISysDeptService;

import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


@Api(tags = "部门表对象功能接口")
@RestController
@RequestMapping("/sysDept")
@Slf4j
public class SysDeptController extends BaseController<ISysDeptService> {


    @ApiOperation("查询所有部门")
    @GetMapping("/findAllDept")
    public Result<?> findAllDept() {
        return findAll();
    }

    @ApiOperation("分页查询")
    @GetMapping("/pageQuery")
    public Result<?> pageQuerySysDept(SysDept sysDept, PageRequest pageRequest, HttpServletRequest request) {
        return super.queryPageList(sysDept, pageRequest,request);
    }

    @ApiOperation("通过ID查询单条数据")
    @GetMapping("/queryById")
    public Result<?> queryDeptById(String id) {
        return findById(id);
    }

    @ApiOperation("新增数据")
    @PostMapping("/save")
    public Result<?> saveDept(@Validated(GroupsClass.InsertClass.class) @RequestBody SysDept sysDept) {
        return save(sysDept);
    }

    @ApiOperation("更新数据")
    @PutMapping("/edit")
    public Result<?> editDept(@Validated(GroupsClass.UpdateClass.class) @RequestBody SysDept sysDept) {
        return updateById(sysDept);
    }

    @ApiOperation("通过主键删除数据")
    @DeleteMapping("/deleteById")
    public Result<?> deleteDeptById(String id) {
        return removeById(id);
    }
}
