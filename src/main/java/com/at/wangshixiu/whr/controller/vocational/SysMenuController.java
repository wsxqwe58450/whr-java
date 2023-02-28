package com.at.wangshixiu.whr.controller.vocational;


import com.at.wangshixiu.whr.common.Result;
import com.at.wangshixiu.whr.controller.BaseController;
import com.at.wangshixiu.whr.entity.sys.SysMenu;
import com.at.wangshixiu.whr.service.ISysMenuService;


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



@Api(tags = "菜单表对象功能接口")
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController extends BaseController<ISysMenuService> {

    @ApiOperation("通过ID查询单条数据")
    @GetMapping("/queryById")
    public Result<?> queryById(String id){
       return super.findById(id);
    }

    @ApiOperation("分页查询")
    @GetMapping("/pageQuery")
    public Result<?> pageQuery(SysMenu sysMenu, PageRequest pageRequest, HttpServletRequest request){
        return super.queryPageList(sysMenu, pageRequest, request);
    }


    @ApiOperation("新增数据")
    @PostMapping("/save")
    public Result<?> add(SysMenu sysMenu){
        return super.save(sysMenu);
    }


    @ApiOperation("更新数据")
    @PutMapping("/edit")
    public Result<?> edit(SysMenu sysMenu){
        return super.updateById(sysMenu);
    }


    @ApiOperation("通过主键删除数据")
    @DeleteMapping("/deleteById")
    public Result<?> deleteById(String id){
        return super.removeById(id);
    }
}
