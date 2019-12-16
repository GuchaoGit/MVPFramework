package com.guc.mvpframework.interceptor;

import android.util.Log;

import com.guc.mvpframework.bean.BeanRequest;
import com.guc.mvpframework.bean.StringRequestBody;

import java.io.IOException;
import java.util.List;
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
        beanRequest.setDataObjId("obj_01");
        beanRequest.setRegionalismCode("01");
        beanRequest.setNetworkCode( "01");
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
            HttpUrl oldUrl = urlBuilder.build();
            HttpUrl.Builder newHttpUrlBuilder = getNewHttpUrlBuilder(oldUrl);

            BeanRequest.Condition condition = new BeanRequest.Condition();
            condition.logicalOperate = "and";
            condition.addKeyValue("apiName",getApiName(oldUrl.pathSegments()));
            RequestBody body = request.body();
            if (body instanceof FormBody) {
                FormBody formBody = (FormBody) body;
                for (int i = 0; i < formBody.size(); i++) {
                    condition.addKeyValue(formBody.name(i), formBody.value(i));
                }
            } else {
                Log.e(TAG, "intercept: params" + body);
            }
            beanRequest.setConditon(condition);
            Log.e(TAG, "intercept: " + beanRequest.toString());
            StringRequestBody newRequestBody = new StringRequestBody(beanRequest.toString());
            Log.e(TAG, "newURL:"+newHttpUrlBuilder.toString() );
            requestBuilder.url(newHttpUrlBuilder.build());
            // 将最终的表单body填充到request中
            requestBuilder.post(newRequestBody);
        }

        // 这里我们可以添加header
//        requestBuilder.addHeader(HEADER_KEY_USER_AGENT, getUserAgent()); // 举例，调用自己业务的getUserAgent方法
        Log.e(TAG, "参数: " + requestBuilder.build().toString());
        return chain.proceed(requestBuilder.build());
    }

    private HttpUrl.Builder getNewHttpUrlBuilder(HttpUrl oldUrl){
        HttpUrl.Builder newHttpUrlBuilder = new HttpUrl.Builder();
        newHttpUrlBuilder.scheme(oldUrl.scheme()) ;
        newHttpUrlBuilder.encodedUsername(oldUrl.encodedUsername());
        newHttpUrlBuilder.encodedPassword (oldUrl.encodedPassword());
        newHttpUrlBuilder.host(oldUrl.host()) ;
        newHttpUrlBuilder.port(oldUrl.port());
        List<String> oldPathSegments = oldUrl.encodedPathSegments();
        newHttpUrlBuilder.addEncodedPathSegment(oldPathSegments.get(0));
        newHttpUrlBuilder.addEncodedPathSegment(oldPathSegments.get(1));
        return newHttpUrlBuilder;
    }

    private String getApiName(List<String> oldPathSegments){
        StringBuilder sbApiName = new StringBuilder();
        if (oldPathSegments.size()>2){
            for (int i = 2;i<oldPathSegments.size();i++){
                sbApiName.append(oldPathSegments.get(i));
                if (i!=oldPathSegments.size()-1){
                    sbApiName.append("/");
                }
            }
        }else {
            sbApiName.append("unknown");
        }
        return sbApiName.toString();
    }
}
