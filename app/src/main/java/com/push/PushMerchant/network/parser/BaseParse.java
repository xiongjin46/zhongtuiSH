package com.push.PushMerchant.network.parser;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.push.PushMerchant.base.BaseResp;

import java.io.StringReader;
import java.lang.reflect.Modifier;

/**
 * @ClassName: BaseParse
 * @Description:
 * @author: YYL
 * <p/>
 * create at 2016/9/6
 */
public class BaseParse<T extends BaseResp> {

    public T parse(String jsonString, Class<?> clazz) throws JsonIOException, JsonSyntaxException {
        Gson gson;
        final int sdk = Build.VERSION.SDK_INT;
        if (sdk >= 23) {
            GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT,
                    Modifier.STATIC);
            gson = gsonBuilder.create();
        } else {
            gson = new Gson();
        }
        JsonReader reader = new JsonReader(new StringReader(jsonString));
        reader.setLenient(true);
        return gson.fromJson(reader, clazz);
    }
}
