package com.guc.mvpframework.interceptor;

import android.util.Log;

import com.guc.mvpframework.bean.BeanRequest;
import com.guc.mvpframework.bean.StringRequestBody;

import java.io.IOException;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by guc on 2019/12/16.
 * 描述：参数拦截器
 */
public class Params2Interceptor implements Interceptor {
    private static final String TAG = "guc_ParamsInterceptor";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String HEADER_KEY_USER_AGENT = "User-Agent";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //region 构建请求
        BeanRequest beanRequest = new BeanRequest();
        beanRequest.messageId = "id_15236181577";
        beanRequest.version = "1.0";
        //endregion
        Request.Builder requestBuilder = request.newBuilder();
        HttpUrl.Builder urlBuilder = request.url().newBuilder();
        if (METHOD_GET.equals(request.method())) { // GET方法
            // 这里可以添加一些公共get参数
            urlBuilder.addEncodedQueryParameter("key_1", "value_1");
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

            BeanRequest.Parameter parameter = new BeanRequest.Parameter();
            beanRequest.parameter = parameter;
            parameter.dataObjId = "obj_01";
            parameter.regionalismCode = "01";
            parameter.networkCode = "01";
            parameter.regionalismCode = "01";
            BeanRequest.Condition condition = new BeanRequest.Condition();
            parameter.condition = condition;
            condition.logicalOperate = "and";
            BeanRequest.Condition.KeyValueListBuilder keyValueListBuilder = new BeanRequest.Condition.KeyValueListBuilder();

            RequestBody body = request.body();
            if (body instanceof FormBody) {
                FormBody formBody = (FormBody) body;
                for (int i = 0; i < formBody.size(); i++) {
                    keyValueListBuilder.addKeyValue(formBody.name(i), formBody.value(i));
                }
            } else {
                Log.e(TAG, "intercept: params" + body);
            }
            condition.keyValueList = keyValueListBuilder.build();

            Log.e(TAG, "intercept: " + beanRequest.toString());
            StringRequestBody newRequestBody = new StringRequestBody(beanRequest.toString());

            // 将最终的表单body填充到request中
            requestBuilder.post(newRequestBody);
        }

        // 这里我们可以添加header
//        requestBuilder.addHeader(HEADER_KEY_USER_AGENT, getUserAgent()); // 举例，调用自己业务的getUserAgent方法
        Log.e(TAG, "参数: " + requestBuilder.build().toString());
        return chain.proceed(requestBuilder.build());
    }
}
