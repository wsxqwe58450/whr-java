package com.at.wangshixiu.whr.entity.related;

/**
 * @Author 王世秀
 * @Date 2023/2/28
 * @Description
 */

import com.at.wangshixiu.whr.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : 王世秀
 * @Date : 2023/2/28
 * @Description: 角色菜单表;
 */
@ApiModel(value = "角色菜单表",description = "角色菜单表实体类")
@TableName("sys_role_menu")
@Data
public class SysRoleMenu extends BaseEntity {
    /** 角色ID */
    @ApiModelProperty("角色ID")
    private String roleId;
    /** 菜单ID */
    @ApiModelProperty("菜单ID")
    private String menuId;
}
