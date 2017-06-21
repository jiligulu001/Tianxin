package com.tianxin.tianxin.callback;


import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.BaseRequest;
import com.tianxin.tianxin.model.OKgoResponse;
import com.tianxin.tianxin.model.SimpleResponse;
import com.tianxin.tianxin.utils.Convert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * 对返回数据为json格式的数据解析的封装
 * <p/>
 * 使用：
 * 该方法是子线程处理，不能做ui相关的工作
 * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
 * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
 * <pre>
 * OkGo.get(Urls.URL_METHOD)
 *     .tag(this)
 *     .execute(new JsonDialogCallback<OKgoResponse<ServerModel>>(this) {
 *          @Override
 *          public void onSuccess(OKgoResponse<ServerModel> responseData, Call call, Response response) {
 *              responseData.data
 *          }
 *     });
 * </pre>
 * <p/>
 * 如果返回数据为string类型
 * <pre>
 * OkGo.get(Urls.URL_METHOD)
 *     .tag(this)
 *     .execute(new StringCallback(this) {
 *          @Override
 *          public void onSuccess(String s, Call call, Response response) {
 *              TextView text.setText（s）
 *          }
 *     });
 * </pre>
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
    }

    @Override
    public T convertSuccess(Response response) throws Exception {

        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        if (!(type instanceof ParameterizedType))
            throw new IllegalStateException("没有填写泛型参数");
        Type rawType = ((ParameterizedType) type).getRawType();
        Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
        JsonReader jsonReader = new JsonReader(response.body().charStream());
        if (typeArgument == Void.class) {
            SimpleResponse simpleResponse = Convert.fromJson(jsonReader, SimpleResponse.class);
            response.close();
            return (T) simpleResponse.toLzyResponse();
        } else if (rawType == OKgoResponse.class) {
            OKgoResponse OKgoResponse = Convert.fromJson(jsonReader, type);
            response.close();
            int code = OKgoResponse.errorCode;

            if (code == 0) {
                return (T) OKgoResponse;
            } else if (code == 1) {
                throw new IllegalStateException("未知错误");
            } else if (code == 3) {
                throw new IllegalStateException("用户名或密码错误");
            } else if (code == 4) {
                throw new IllegalStateException("未登录");
            } else if (code == 100) {
                throw new IllegalStateException("参数错误");
            } else if (code == 900) {
                throw new IllegalStateException("无权访问数据");
            } else {
                throw new IllegalStateException("错误代码：" + code + "，错误信息：" + OKgoResponse.errorMsg);
            }
        } else {
            response.close();
            throw new IllegalStateException("基类错误无法解析!");
        }
    }
}