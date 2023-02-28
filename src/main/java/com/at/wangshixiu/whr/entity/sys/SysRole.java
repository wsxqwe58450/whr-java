package com.at.wangshixiu.whr.entity.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.at.wangshixiu.whr.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@ApiModel(value = "角色表",description = "角色表实体类")
@TableName("sys_role")
@Data
public class SysRole extends BaseEntity {
    /** 角色名称 */
    @ApiModelProperty("角色名称")
    private String roleName;
    /** 描述 */
    @ApiModelProperty("描述")
    private String describe;
}
