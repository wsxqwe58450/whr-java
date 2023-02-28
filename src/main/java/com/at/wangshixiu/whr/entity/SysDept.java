package com.at.wangshixiu.whr.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author 王世秀
 * @Date 2022/12/20
 * @Description 部门表实体类
 */
@Data
@TableName("sys_dept")
@ApiModel(value = "部门表", description = "")
public class SysDept implements Serializable {
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
    /**
     * id
     */
    @ApiModelProperty("id")
    @TableId
    private String id;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;
    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    @TableField(fill = FieldFill.UPDATE)
    private String updatedBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.UPDATE)
    private Date updatedTime;

}
