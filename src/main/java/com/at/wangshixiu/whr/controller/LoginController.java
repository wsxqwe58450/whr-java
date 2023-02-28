package com.at.wangshixiu.whr.controller;

import com.at.wangshixiu.whr.common.LoginProperties;
import com.at.wangshixiu.whr.common.Result;
import com.at.wangshixiu.whr.common.enmus.LoginCodeEnum;
import com.at.wangshixiu.whr.dto.LoginUserDto;
import com.at.wangshixiu.whr.service.ISysUserService;
import com.wf.captcha.base.Captcha;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.ApiOperation;

/**
 * @Author 王世秀
 * @Date 2022/12/24
 * @Description
 */

@RestController
@RequestMapping("/sys")
@Validated
public class LoginController {
    @Resource
    private LoginProperties loginProperties;
    @Resource
    private ISysUserService iSysUserService;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @GetMapping("/code")
    @ApiOperation("获取验证码")
    public Result<?> getCode() {
        Captcha captcha = loginProperties.getCaptcha();
        long currentTimeMillis = System.currentTimeMillis();
        //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
        String captchaValue = captcha.text();
        if (captcha.getCharType() - 1 == LoginCodeEnum.ARITHMETIC.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        // 保存
        redisTemplate.opsForValue().set(String.valueOf(currentTimeMillis), captchaValue, loginProperties.getLoginCode().getExpiration(), TimeUnit.MINUTES);
        HashMap<String, Object> objectHashMap = new HashMap<>(16);
        objectHashMap.put("key",currentTimeMillis);
        objectHashMap.put("code",captcha.toBase64());
        return Result.ok(objectHashMap);

    }

    @PostMapping("/login")
    @ApiOperation("登录")
    public Result<?> login(@Validated @RequestBody LoginUserDto loginUserDto, HttpServletRequest request) {
        return iSysUserService.handleLogin(loginUserDto, request);
    }
}
