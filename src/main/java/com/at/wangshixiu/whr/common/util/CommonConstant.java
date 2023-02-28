package com.at.wangshixiu.whr.common.util;

/**
 * @Author 王世秀
 * @Date 2023/2/28
 * @Description
 */

public class CommonConstant {

    public static class Login {

        public final static String VERIFICATION_CODE_EXPIRE = "验证码已过期";
        public final static String VERIFICATION_CODE_ERROR = "验证码错误";
        public final static String LOGIN_SUCCESS = "登录成功";
        public final static String LOGIN_ERROR = "登录失败";
    }

    public static class User {

        public static final String FORBIDDEN_STATE = "0";
        public final static String USERNAME_PASSWORD_ERROR = "用户名或密码错误";
        public final static String USER_IS_NOT_AVAILABLE = "账户已被禁用,请联系管理员解除禁用";
        public final static String USER_IS_NOT_EXIST = "用户已存在,请勿重复注册";
        public final static String USER_SAVE_SUCCESS = "用户注册成功";
        public final static String USER_SAVE_ERROR = "用户注册失败";

    }

}
