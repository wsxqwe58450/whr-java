package com.at.wangshixiu.whr.entity.sys;

import com.at.wangshixiu.whr.common.interfaces.Desensitized;
import com.at.wangshixiu.whr.common.sensitive.SensitiveTypeEnum;
import com.at.wangshixiu.whr.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author 王世秀
 * @Date 2022/12/20
 * @Description 用户实体类
 */
@Data
@TableName("sys_user")
@ApiModel(value = "sys_user", description = "用户实体类")
public class SysUser extends BaseEntity  {

    /**
     * 用户呢称
     */
    @ApiModelProperty("用户呢称")
    @NotEmpty(message = "用户名不能为空")
    private String username;
    /**
     * 用户的真实姓名
     */
    @ApiModelProperty("用户的真实姓名")
    @Desensitized(type = SensitiveTypeEnum.CHINESE_NAME)
    private String realName;
    /**
     * 用户密码
     */
    @ApiModelProperty("用户密码")
    @NotEmpty(message = "密码不能为空")
    @Desensitized(type = SensitiveTypeEnum.PASSWORD)
    private String password;
    /**
     * 用户性别
     */
    @ApiModelProperty("用户性别 0男 1女")
    private String sex;
    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    @Pattern(regexp = "[0-9-()（）]{7,18}", message = "请输入正确的手机号")
    @Desensitized(type = SensitiveTypeEnum.MOBILE_PHONE)
    private String phone;
    /**
     * 邮件
     */
    @ApiModelProperty("邮件")
    @Email(message = "请输入正确的邮箱")
    @Desensitized(type = SensitiveTypeEnum.EMAIL)
    private String email;
    /**
     * 家庭地址
     */
    @ApiModelProperty("家庭地址")
    @Desensitized(type = SensitiveTypeEnum.ADDRESS)
    private String address;
    /**
     * 头像url
     */
    @ApiModelProperty("头像url")
    private String headPortrait;
    /**
     * 状态 1正常  0 禁用
     */
    @ApiModelProperty("状态 1正常  0 禁用  2锁定")
    private String state;

    /**
     * 部门code
     */
    @ApiModelProperty("部门code")
    private String deptCode;

    /**
     * IP
     */
    @ApiModelProperty("IP")
    private String ip;
}
