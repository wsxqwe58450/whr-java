package com.at.wangshixiu.whr.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.Api;
import lombok.Data;

/**
 * @Author 王世秀
 * @Date 2022/12/24
 * @Description
 */
@Data
@Api("用户登录实体")
public class LoginUserDto implements Serializable {
    @NotBlank(message = "用户名不能为空")
    private String userName;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "验证码不能为空")
    private String code;
    private String key;
}
