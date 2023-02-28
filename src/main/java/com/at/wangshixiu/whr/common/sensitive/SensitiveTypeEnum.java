package com.at.wangshixiu.whr.common.sensitive;

/**
 * @Author 王世秀
 * @Date 2022/12/20
 * @Description 数据脱敏枚举
 */
public enum SensitiveTypeEnum {
    /**
     * 中文名
     */
    CHINESE_NAME,
    /**
     * 身份证号
     */
    ID_CARD,
    /**
     * 座机号
     */
    FIXED_PHONE,
    /**
     * 手机号
     */
    MOBILE_PHONE,
    /**
     * 地址
     */
    ADDRESS,
    /**
     * 电子邮件
     */
    EMAIL,
    /**
     * 银行卡
     */
    BANK_CARD,
    /**
     * 公司开户银行联号
     */
    CNAPS_CODE,
    /**
     * 密码
     */
    PASSWORD;
}
