package com.at.wangshixiu.whr.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author 王世秀
 * @Date 2022/12/24
 * @Description 字符串工具类
 */

public class MyStringUtils extends org.apache.commons.lang.StringUtils {
    public static void main(String[] args) {
        String substring = "getCreateTime".substring(3);
        System.out.println(toLowerCaseFirstOne(substring));
    }

    /**
     * 驼峰转下划线
     * @param str   目标字符串
     * @return:
     */
    public static String humpToUnderline(String str) {
        String regex = "([A-Z])";
        Matcher matcher = Pattern.compile(regex).matcher(str);
        while (matcher.find()) {
            String target = matcher.group();
            str = str.replaceAll(target, "_"+target.toLowerCase());
        }
        return str;
    }

    /**
     * 下划线转驼峰
     * @param str  目标字符串
     * @return:
     */
    public static String underlineToHump(String str) {
        String regex = "_(.)";
        Matcher matcher = Pattern.compile(regex).matcher(str);
        while (matcher.find()) {
            String target = matcher.group(1);
            str = str.replaceAll("_"+target, target.toUpperCase());
        }
        return str;
    }


    /**
     * 字符串首字母转小写
     * @apiNote
     * @param s 目标字符串
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return Character.toLowerCase(s.charAt(0)) + s.substring(1);
        }
    }


    /***
     * 字符串首字母转大写
     * @param s 目标字符串
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return Character.toUpperCase(s.charAt(0)) + s.substring(1);
        }
    }

    /**
     * 字符串不为null和空字符串
     * @param s
     * @return
     */
    public static boolean isNotNull(String s){
        return null != s && s.trim().length()>0;
    }
}
