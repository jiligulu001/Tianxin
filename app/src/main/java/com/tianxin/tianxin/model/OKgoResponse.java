package com.tianxin.tianxin.model;

import java.io.Serializable;

/**
 * 网络请求返回数据解析
 * 对errorCode和errorMsg做统一的处理
 * 使用时需要根据后台返回的格式做适当的调整
 * <p/>
 * errorCode 表示请求返回的code
 * errorMsg  表示请求返回的信息
 * data  表示请求返回的具体的内容
 */
public class OKgoResponse<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;

    public int    errorCode;
    public String errorMsg;
    public T      data;
}