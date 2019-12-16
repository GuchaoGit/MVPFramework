package com.guc.mvpframework.bean;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guc on 2019/12/16.
 * 描述：请求bean
 */
public class BeanRequest {
    public String messageId;
    public String version;
    private Parameter parameter;

    public BeanRequest(){
        parameter = new Parameter();
    }

    public void setDataObjId(String dataObjId){
        parameter.dataObjId = dataObjId;
    }
    public void setRegionalismCode(String regionalismCode){
        parameter.regionalismCode = regionalismCode;
    }
    public void setNetworkCode(String networkCode){
        parameter.networkCode = networkCode;
    }
    public void setConditon(Condition conditon){
        parameter.condition = conditon;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    private static class Parameter {
        public String dataObjId;
        public String regionalismCode;
        public String networkCode;
        public Condition condition;

    }

    public static class Condition {
        public String logicalOperate = "and";
        private List<KeyValue> keyValueList;

        public Condition(){
            keyValueList = new ArrayList<>();
        }

        public Condition addKeyValue(String key, String value) {
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                keyValueList.add(new KeyValue(key, value));
            }
            return this;
        }
        private static class KeyValue {
            public String key;
            public String relationOperator = "=";
            public String value;

            public KeyValue(String key, String value) {
                this.key = key;
                this.value = value;
            }
        }
    }
}



