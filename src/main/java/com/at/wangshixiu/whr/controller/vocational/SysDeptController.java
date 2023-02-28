package com.at.wangshixiu.whr.controller.vocational;

import com.at.wangshixiu.whr.common.Result;
import com.at.wangshixiu.whr.controller.BaseController;
import com.at.wangshixiu.whr.entity.sys.SysDept;
import com.at.wangshixiu.whr.service.ISysDeptService;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author 王世秀
 * @Date 2022/12/24
 * @Description 部门表(sys_dept)控制层
 */
@Api(tags = "部门表对象功能接口")
@RestController
@RequestMapping("/sysDept")
@Slf4j
public class SysDeptController extends BaseController<ISysDeptService> {


    @ApiOperation("查询所有部门")
    @GetMapping("/findAllDept")
    public Result<?> findAllDept() {
        return super.findAll();
    }

    @ApiOperation("分页查询")
    @GetMapping("/pageQuery")
    public Result<?> pageQuery(SysDept sysDept, PageRequest pageRequest, HttpServletRequest request) {
        return super.queryPageList(sysDept, pageRequest,request);
    }

    @ApiOperation("通过ID查询单条数据")
    @GetMapping("/queryById")
    public Result<?> queryById(String id) {
        return super.findById(id);
    }

    @ApiOperation("新增数据")
    @PostMapping("/add")
    public Result<?> add(SysDept sysDept) {
        return super.save(sysDept);
    }

    @ApiOperation("更新数据")
    @PutMapping("/edit")
    public Result<?> edit(SysDept sysDept) {
        return super.updateById(sysDept);
    }

    @ApiOperation("通过主键删除数据")
    @DeleteMapping("/deleteById")
    public Result<?> deleteById(String id) {
        return super.removeById(id);
    }
}
