package com.at.wangshixiu.whr.entity.related;

import com.at.wangshixiu.whr.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author 王世秀
 * @Date 2023/2/28
 * @Description
 */

@Data
@ApiModel(value = "用户角色关联表",description = "")
@TableName("sys_user_role")
public class SysUserRole extends BaseEntity {

    /** 用户ID */
    @ApiModelProperty(name = "用户ID",notes = "")
    private String userId ;
    /** 角色ID */
    @ApiModelProperty(name = "角色ID",notes = "")
    private String roleId ;

}
