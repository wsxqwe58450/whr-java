package com.at.wangshixiu.whr.controller;

import com.at.wangshixiu.whr.common.GeneratorQueryWrapper;
import com.at.wangshixiu.whr.common.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;


/**
 * @Author 王世秀
 * @Date 2022/12/24
 * @Description 控制层基类
 */
@SuppressWarnings("unchecked")
public class BaseController<S extends IService> {

    @Autowired
    protected S baseService;

    public <T extends Serializable> Result<?> findAll() {
        return Result.ok(baseService.list());
    }

    public <T extends Serializable> Result<?> queryPageList(T obj, PageRequest pageRequest, HttpServletRequest request) {
        Page<T> sysUserPage = new Page<>(pageRequest.getPageNumber(), pageRequest.getPageSize());
        QueryWrapper<T> queryWrapper = GeneratorQueryWrapper.initQueryWrapper(obj,request);
        return Result.ok(baseService.page(sysUserPage, queryWrapper));
    }

    public Result<?> findById(String id) {
        return Result.ok(baseService.getById(id));
    }

    public <T extends Serializable> Result<?> save(T obj) {
        baseService.save(obj);
        return Result.ok();
    }

    public <T extends Serializable> Result<?> updateById(T obj) {
        baseService.updateById(obj);
        return Result.ok();
    }

    public <T extends Serializable> Result<?> removeById(String id) {
        baseService.removeById(id);
        return Result.ok();
    }

    public IService getBaseService() {
        return baseService;
    }
}
