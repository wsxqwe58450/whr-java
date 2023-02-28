package com.at.wangshixiu.whr.entity.sys;

import com.at.wangshixiu.whr.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@TableName("sys_dept")
@ApiModel(value = "部门表", description = "部门实体类")
public class SysDept extends BaseEntity {
    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String deptName;
    /**
     * 部门负责人id
     */
    @ApiModelProperty("部门负责人id")
    private String deptDutyPersonId;
    /**
     * 上级部门id
     */
    @ApiModelProperty("上级部门id")
    private String parentId;
    /**
     * 部门描述
     */
    @ApiModelProperty("部门描述")
    private String deptDescribe;
    /**
     * 是否可用 0 不可用 1可用
     */
    @ApiModelProperty("是否可用 0 不可用 1可用")
    private String state;

}
