package com.at.wangshixiu.whr.entity.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.at.wangshixiu.whr.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;


@ApiModel(value = "菜单表",description = "菜单表实体类")
@TableName("sys_menu")
@Data
public class SysMenu extends BaseEntity {
    /** 表ID */
    @ApiModelProperty("表ID")
    @TableId
    private String id;
    /** 菜单名称 */
    @ApiModelProperty("菜单名称")
    private String menuName;
    /** 父id */
    @ApiModelProperty("父id")
    private String parentId;
    /** 组件路径 */
    @ApiModelProperty("组件路径")
    private String componentUrl;
    /** 类型(0 目录 1菜单 2按钮) */
    @ApiModelProperty("类型(0 目录 1菜单 2按钮)")
    private String type;
    /** 按钮名称 */
    @ApiModelProperty("按钮名称")
    private String buttonName;
    /** 权限标识 */
    @ApiModelProperty("权限标识")
    private String permissions;
    /** 序号 */
    @ApiModelProperty("序号")
    private String orderNumber;
}
