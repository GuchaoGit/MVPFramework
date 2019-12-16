package com.guc.mvpframework.bean;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;

/**
 * Created by guc on 2019/12/16.
 * 描述：字符串RequestBody
 */
public class StringRequestBody extends RequestBody {
    private static final MediaType CONTENT_TYPE =
//            MediaType.parse("application/x-www-form-urlencoded");
            MediaType.parse("application/json");
    private String content;

    public StringRequestBody(String content) {
        this.content = content;
    }

    @Override
    public MediaType contentType() {
        return CONTENT_TYPE;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        writeOrCountBytes(sink, false);
    }

    @Override
    public long contentLength() throws IOException {
        return writeOrCountBytes(null, true);
    }

    private long writeOrCountBytes(@Nullable BufferedSink sink, boolean countBytes) {
        long byteCount = 0L;
        Buffer buffer;
        if (countBytes) {
            buffer = new Buffer();
        } else {
            buffer = sink.buffer();
        }
        buffer.writeUtf8(content);
        if (countBytes) {
            byteCount = buffer.size();
            buffer.clear();
        }
        return byteCount;
    }
}
