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
    public Parameter parameter;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static class Parameter {
        public String dataObjId;
        public String regionalismCode;
        public String networkCode;
        public Condition condition;

    }

    public static class Condition {
        public String logicalOperate = "and";
        public List<KeyValue> keyValueList;

        public static class KeyValue {
            public String key;
            public String relationOperator = "=";
            public String value;

            public KeyValue(String key, String value) {
                this.key = key;
                this.value = value;
            }
        }

        public static class KeyValueListBuilder {
            List<KeyValue> mKeyValues;

            public KeyValueListBuilder() {
                mKeyValues = new ArrayList<>();
            }

            public KeyValueListBuilder addKeyValue(String key, String value) {
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    mKeyValues.add(new KeyValue(key, value));
                }
                return this;
            }

            public List<KeyValue> build() {
                return mKeyValues;
            }
        }
    }
}



