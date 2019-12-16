package com.guc.mvpframework.interceptor;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by guc on 2019/12/13.
 * 描述：返回拦截器
 */
public class ResponseInterceptor implements Interceptor {
    private static final String TAG = "guc_ResponseInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            Log.e(TAG, "intercept: " + e.toString());
            throw e;
        }
        Response.Builder builder = response.newBuilder();

        if (response.isSuccessful()) {
            ResponseBody responseBody = response.body();
            String result = responseBody.string();
            Log.e(TAG, "result: " + result);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(result);
                if (jsonObject.has("message")){
                    result = jsonObject.getString("message");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e(TAG, "trans_result: "+ result );
            ResponseBody newBody = null;
            try {
                newBody = ResponseBody.create(null, result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            builder.body(newBody);
        }
        return builder.build();
    }
}
