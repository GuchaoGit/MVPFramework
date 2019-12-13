package com.guc.mvpframework.interceptor;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by guc on 2019/12/13.
 * 描述：参数拦截器
 */
public class ParamsInterceptor implements Interceptor {
    private static final String TAG = "guc_ParamsInterceptor";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String HEADER_KEY_USER_AGENT = "User-Agent";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        HttpUrl.Builder urlBuilder = request.url().newBuilder();
        if (METHOD_GET.equals(request.method())) { // GET方法
            // 这里可以添加一些公共get参数
            urlBuilder.addEncodedQueryParameter("key_xxx", "value_xxx");
            HttpUrl httpUrl = urlBuilder.build();

            // 打印所有get参数
            Set<String> paramKeys = httpUrl.queryParameterNames();
            for (String key : paramKeys) {
                String value = httpUrl.queryParameter(key);
                Log.e(TAG, "intercept: " + key + ":" + value);
            }
            // 将最终的url填充到request中
            requestBuilder.url(httpUrl);
        } else if (METHOD_POST.equals(request.method())) { // POST方法
            // FormBody和url不太一样，若需添加公共参数，需要新建一个构造器
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            RequestBody body = request.body();
            // 把已有的post参数添加到新的构造器
            if (body instanceof FormBody) {
                FormBody formBody = (FormBody) body;
                bodyBuilder.add("data", getParamString(formBody));
            } else {
                Log.e(TAG, "intercept: params" + body);
            }

            // 这里可以添加一些公共post参数
//            bodyBuilder.addEncoded("data", getParamString(body));
            FormBody newBody = bodyBuilder.build();
//            // 打印所有post参数
            for (int i = 0; i < newBody.size(); i++) {
                Log.e(TAG, "new  " + newBody.name(i) + ":" + newBody.value(i));
            }
            // 将最终的表单body填充到request中
            requestBuilder.post(newBody);
        }

        // 这里我们可以添加header
//        requestBuilder.addHeader(HEADER_KEY_USER_AGENT, getUserAgent()); // 举例，调用自己业务的getUserAgent方法
        Log.e(TAG, "intercept: " + requestBuilder.build().toString());
        return chain.proceed(requestBuilder.build());
    }

    private String getParamString(FormBody formBody) {
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < formBody.size(); i++) {
            try {
                jsonObject.put(formBody.name(i), formBody.value(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject.toString();
    }
}
