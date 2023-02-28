package com.at.wangshixiu.whr.common.util;

import com.at.wangshixiu.whr.common.Result;
import com.at.wangshixiu.whr.common.interfaces.Desensitized;


import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.ArrayUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author 王世秀
 * @Date 2022/12/20
 * @Description 脱敏工具类
 */
@Slf4j
public class DesensitizedUtil {

    /**
     * 脱敏工具开关，0：关闭，1：开启
     */
    public static final String DESENT_STATUS = "1";

    /**
     * 脱敏数据
     *
     * @param obj
     * @return void
     */
    public static void desentData(Object obj) throws IllegalAccessException {
        if (null == obj) {
            return;
        }

        //如果是原始类型，则忽略处理
        if (obj.getClass().isPrimitive()) {
            return;
        }
        // 是否是接口
        if (obj.getClass().isInterface()) {
            return;
        }

        Object data = null;
        Class<?> clazz = null;
        //如果是通用的分页响应对象，则对该对象内部的List<T>进行脱敏
        if (obj.getClass().equals(Result.class)) {
            data = ((Result) obj).getData();//这里是自定义返回对象
            clazz = data.getClass();
            if (null == clazz) {
                return;
            }
        }

        // 获取所有属性
        Field[] fields = clazz.getDeclaredFields();
        while (null != clazz.getSuperclass() && !Object.class.equals(clazz.getSuperclass())) {

            fields = (Field[]) ArrayUtils.addAll(fields, clazz.getSuperclass().getDeclaredFields());
            clazz = clazz.getSuperclass();
        }

        if (null == fields && fields.length == 0) {
            return;
        }

        for (Field field : fields) {
            field.setAccessible(true);
            if (null == field) {
                return;
            }
            Object value = field.get(data);
            if (null != value) {
                Class<?> type = value.getClass();

                // 1.处理子属性，包括集合中的
                if (type.isArray()) {
                    int len = Array.getLength(value);
                    for (int i = 0; i < len; i++) {
                        Object arrayObject = Array.get(value, i);
                        DesensitizedUtil.desentData(arrayObject);
                    }
                } else if (value instanceof Collection<?>) {
                    Collection<?> c = (Collection<?>) value;
                    Iterator<?> it = c.iterator();
                    while (it.hasNext()) {
                        Object collectionObj = it.next();
                        DesensitizedUtil.desentData(collectionObj);
                    }
                } else if (value instanceof Map<?, ?>) {
                    Map<?, ?> m = (Map<?, ?>) value;
                    Set<?> set = m.entrySet();
                    for (Object o : set) {
                        Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
                        Object mapVal = entry.getValue();
                        DesensitizedUtil.desentData(mapVal);
                    }
                } else if (!type.isPrimitive()
                        && !MyStringUtils.startsWith(type.getPackage().getName(), "javax.")
                        && !MyStringUtils.startsWith(type.getPackage().getName(), "java.")
                        && !MyStringUtils.startsWith(field.getType().getName(), "javax.")
                        && !MyStringUtils.startsWith(field.getName(), "java.")) {
                    DesensitizedUtil.desentData(type);
                }
            }

            // 2. 处理自身的属性
            Desensitized annotation = field.getDeclaredAnnotation(Desensitized.class);
            if (field.getType().equals(String.class) && null != annotation) {
                String valueStr = (String) field.get(data);
                if (MyStringUtils.isNotBlank(valueStr)) {
                    switch (annotation.type()) {
                        case CHINESE_NAME: {
                            field.set(data, DesensitizedUtil.chineseName(valueStr));
                            break;
                        }
                        case ID_CARD: {
                            field.set(data, DesensitizedUtil.idCardNum(valueStr));
                            break;
                        }
                        case FIXED_PHONE: {
                            field.set(data, DesensitizedUtil.fixedPhone(valueStr));
                            break;
                        }
                        case MOBILE_PHONE: {
                            field.set(data, DesensitizedUtil.mobilePhone(valueStr));
                            break;
                        }
                        case ADDRESS: {
                            field.set(data, DesensitizedUtil.address(valueStr, 4));
                            break;
                        }
                        case EMAIL: {
                            field.set(data, DesensitizedUtil.email(valueStr));
                            break;
                        }
                        case BANK_CARD: {
                            field.set(data, DesensitizedUtil.bankCard(valueStr));
                            break;
                        }
                        case CNAPS_CODE: {
                            field.set(data, DesensitizedUtil.cnapsCode(valueStr));
                            break;
                        }
                        case PASSWORD:{
                            field.set(data, DesensitizedUtil.password(valueStr));
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                }
            }
        }

    }


    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     *
     * @param fullName 中文姓名
     * @return 中文姓名
     */
    private static String chineseName(String fullName) {
        if (MyStringUtils.isBlank(fullName)) {
            return "";
        }
        String name = MyStringUtils.left(fullName, 1);
        return MyStringUtils.rightPad(name, MyStringUtils.length(fullName), "*");
    }

    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     *
     * @param familyName 中文姓名
     * @param givenName  中文姓名
     * @return
     */
    private static String chineseName(String familyName, String givenName) {
        if (MyStringUtils.isBlank(familyName) || MyStringUtils.isBlank(givenName)) {
            return "";
        }
        return chineseName(familyName + givenName);
    }

    /**
     * [身份证号] 显示最后四位，其他隐藏。共计18位或者15位。<例子：*************5762>
     *
     * @param id 身份证号
     * @return 身份证号
     */
    private static String idCardNum(String id) {
        if (MyStringUtils.isBlank(id)) {
            return "";
        }
        String num = MyStringUtils.right(id, 4);
        return MyStringUtils.leftPad(num, MyStringUtils.length(id), "*");
    }

    /**
     * [固定电话] 后四位，其他隐藏<例子：****1234>
     *
     * @param num 固定电话
     * @return 固定电话
     */
    private static String fixedPhone(String num) {
        if (MyStringUtils.isBlank(num)) {
            return "";
        }
        return MyStringUtils.leftPad(MyStringUtils.right(num, 4), MyStringUtils.length(num), "*");
    }

    /**
     * [手机号码] 前三位，后四位，其他隐藏<例子:138******1234>
     *
     * @param num 手机号码
     * @return String
     */
    private static String mobilePhone(String num) {
        if (MyStringUtils.isBlank(num)) {
            return "";
        }
        return MyStringUtils.left(num, 3).concat(MyStringUtils.removeStart(MyStringUtils.leftPad(MyStringUtils.right(num, 4), MyStringUtils.length(num), "*"), "***"));
    }

    /**
     * [地址] 只显示到地区，不显示详细地址；我们要对个人信息增强保护<例子：北京市海淀区****>
     *
     * @param address       地址
     * @param sensitiveSize 敏感信息长度
     * @return String
     */
    private static String address(String address, int sensitiveSize) {
        if (MyStringUtils.isBlank(address)) {
            return "";
        }
        int length = MyStringUtils.length(address);
        return MyStringUtils.rightPad(MyStringUtils.left(address, length - sensitiveSize), length, "*");
    }

    /**
     * [电子邮箱] 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示<例子:g**@163.com>
     *
     * @param email 电子邮箱
     * @return String
     */
    private static String email(String email) {
        if (MyStringUtils.isBlank(email)) {
            return "";
        }
        int index = MyStringUtils.indexOf(email, "@");
        if (index <= 1) {
            return email;
        } else {
            return MyStringUtils.rightPad(MyStringUtils.left(email, 1), index, "*").concat(MyStringUtils.mid(email, index, MyStringUtils.length(email)));
        }
    }

    /**
     * [银行卡号] 前六位，后四位，其他用星号隐藏每位1个星号<例子:6222600**********1234>
     *
     * @param cardNum 银行卡号
     * @return String
     */
    private static String bankCard(String cardNum) {
        if (MyStringUtils.isBlank(cardNum)) {
            return "";
        }
        return MyStringUtils.left(cardNum, 6).concat(MyStringUtils.removeStart(MyStringUtils.leftPad(MyStringUtils.right(cardNum, 4), MyStringUtils.length(cardNum), "*"), "******"));
    }

    /**
     * [公司开户银行联号] 公司开户银行联行号,显示前两位，其他用星号隐藏，每位1个星号<例子:12********>
     *
     * @param code 卡号
     * @return java.lang.String
     */
    private static String cnapsCode(String code) {
        if (MyStringUtils.isBlank(code)) {
            return "";
        }
        return MyStringUtils.rightPad(MyStringUtils.left(code, 2), MyStringUtils.length(code), "*");
    }

    /**
     *  设置为null
     * @param password 密码
     * @return null
     */
    private static String password(String password){
        return null;
    }
}
