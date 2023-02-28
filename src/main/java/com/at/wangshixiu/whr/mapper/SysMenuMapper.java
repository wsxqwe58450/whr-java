package com.at.wangshixiu.whr.mapper;

import com.at.wangshixiu.whr.dto.MenuDto;
import com.at.wangshixiu.whr.entity.sys.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Author 王世秀
 * @Date 2023/2/28
 * @Description 菜单表(sys_menu)数据库访问层
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据登录用户的id查询其对应的菜单
     * @param loginId 登录用户的id
     * @return 封装的菜单对象
     */
    List<MenuDto> getUserMenu(@Param("loginId") String loginId);

    /**
     * 根据父id查询子数据
     * @param parentId 父id
     * @return 子数据
     */
    List<MenuDto> getMenuDtoListByParentId(@Param("parentId") String parentId);
}
