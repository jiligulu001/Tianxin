package com.tianxin.tianxin.config;

/**
 * Created by PC大佬 on 2017/3/30.
 * <p>
 * 项目IP地址和常量配置类
 * 使用时在自己所配置的IP或者常量后注释配置的内容，以便后期查看；
 */
public final class Constants {

    /**
     * 上半部分为IP地址配置
     * =======================================
     * <p>
     * <p>
     * 要注释  要注释  要注释
     * <p>
     * <p>
     * =======================================
     */
    //登陆url ： http://58.211.184.75:8010/login/    参数 {"username": ,"password": } =>POST
    //获取PLC设备列表url ：http://58.211.184.75:8010/device/   => GET
    //获取单个选中的device当前数据 url ： http://58.211.184.75:8010/plc/device_name/ =>GET
    //ex：http://58.211.184.75:8010/plc/20140010/
    public static final String LOGIN      = "http://10.0.2.2:8089/login/";
    public static final String GETPLCLIST = "http://10.0.2.2:8089/test/";
    public static final String PLCINFO    = "http://10.0.2.2:8089/testSetting/";

    /**
     * 下半部分为系统常量
     * =======================================
     * <p>
     * <p>
     * 要注释  要注释  要注释
     * <p>
     * <p>
     * ========================================
     */

    public static final String  APP_DATA     = "tianxin";//Preference文件的名字
    public static       String  IS_FIRST     = "is_first";//是否第一次开启app
    public static       boolean isFirstStart = true;//第一次打开应用
    public static       String  IP           = "ip";
    public static       String  PORT         = "port";
    public static       String  long_login   = "long_login";
}
