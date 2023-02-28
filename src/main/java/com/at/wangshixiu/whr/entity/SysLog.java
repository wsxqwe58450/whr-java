package com.at.wangshixiu.whr.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 王世秀
 * @Date 2022/12/20
 * @Description 日志表实体类
 */

@Data
@TableName("sys_log")
@ApiModel(value = "sys_log", description = "日志实体类")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysLog implements Serializable {
    /**
     * 日志ID
     */
    @ApiModelProperty("日志ID")
    private String id;
    /**
     * 类名称
     */
    @ApiModelProperty("类名称")
    private String className;
    /**
     * 方法名称
     */
    @ApiModelProperty("方法名称")
    private String methodName;
    /**
     * 参数
     */
    @ApiModelProperty("参数")
    private String args;
    /**
     * 所用时间
     */
    @ApiModelProperty("所用时间")
    private Long millisecond;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;
    /**
     * 返回的数据
     */
    @ApiModelProperty("返回的数据")
    private String returnData;
    /**
     * 操作类型
     */
    @ApiModelProperty("操作类型")
    private String operationType;


    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
