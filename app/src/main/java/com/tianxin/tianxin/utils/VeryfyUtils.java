package com.tianxin.tianxin.utils;

import java.util.regex.Pattern;

/**
 * @创建者 戴涛.
 * @创建时间 2016/4/29 13:56.
 * @描述 ${用于校验用户名、密码、手机号、邮箱和身份证等信息}.
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class VeryfyUtils {
    /**
     * 校验器：利用正则表达式校验邮箱、手机号等
     *
     * @author liujiduo
     *
     */

        /**
         * 正则表达式：验证用户名
         */
        public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

        /**
         * 正则表达式：验证密码
         */
        public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,20}$";

        /**
         * 正则表达式：验证手机号
         */
        public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(17[^4,\\D])|(18[0,5-9]))\\d{8}$";
//        public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,2-9]))\\d{8}$";

        /**
         * 正则表达式：验证邮箱
         */
        public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

        /**
         * 正则表达式：验证汉字
         */
        public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

        /**
         * 正则表达式：验证身份证
         */
        public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

        /**
         * 正则表达式：验证URL
         *//*
        public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";*/

        /**
         * 正则表达式：验证IP地址
         */
        public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

        /**
         * 正则表达式：验证非零整数
         */
        public static final String REGEX_NONE_ZERO_INT = "^([1-9]\\d*|0)$";


        /**
         * 校验用户名
         *
         * @param username
         * @return 校验通过返回true，否则返回false
         */
        public static boolean isUsername(String username) {
            return Pattern.matches(REGEX_USERNAME, username);
        }

        /**
         * 校验密码
         *
         * @param password
         * @return 校验通过返回true，否则返回false
         */
        public static boolean isPassword(String password) {
            return Pattern.matches(REGEX_PASSWORD, password);
        }

        /**
         * 校验手机号
         *
         * @param mobile
         * @return 校验通过返回true，否则返回false
         */
        public static boolean isMobile(String mobile) {
            return Pattern.matches(REGEX_MOBILE, mobile);
        }

        /**
         * 校验邮箱
         *
         * @param email
         * @return 校验通过返回true，否则返回false
         */
        public static boolean isEmail(String email) {
            return Pattern.matches(REGEX_EMAIL, email);
        }

        /**
         * 校验汉字
         *
         * @param chinese
         * @return 校验通过返回true，否则返回false
         */
        public static boolean isChinese(String chinese) {
            return Pattern.matches(REGEX_CHINESE, chinese);
        }

        /**
         * 校验身份证
         *
         * @param idCard
         * @return 校验通过返回true，否则返回false
         */
        public static boolean isIDCard(String idCard) {
            return Pattern.matches(REGEX_ID_CARD, idCard);
        }

        /**
         * 校验URL
         *
         * @param url
         * @return 校验通过返回true，否则返回false
         *//*
        public static boolean isUrl(String url) {
            return Pattern.matches(REGEX_URL, url);
        }*/



        /**
         * 校验IP地址
         *
         * @param noZeroInt
         * @return boolean
         */
        public static boolean isNONE_ZERO_INT(String noZeroInt) {
            return Pattern.matches(REGEX_NONE_ZERO_INT, noZeroInt);
        }

    /**
     * 校验非零整数
     */

        //        public static void main(String[] args) {
//            String username = "fdsdfsdj";
//            System.out.println(Validator.isUsername(username));
//            System.out.println(Validator.isChinese(username));
//        }

}
