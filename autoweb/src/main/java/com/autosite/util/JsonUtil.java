package com.autosite.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonUtil {

    public JSONObject covertToJsonObj(Map data) {
        JSONObject ret = new JSONObject();

        for (Iterator iterator = data.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            Object value = data.get(key);

            if (value instanceof List) {
                JSONArray jsonArray = covertToJsonArray((List) value);
                ret.put(key, jsonArray);
            }
            else if (value instanceof Map) {
                JSONObject jsonObj = covertToJsonObj((Map) value);
                ret.put(key, jsonObj);
            }
            else {
                ret.put(key, data.get(key));
            }
        }

        return ret;
    }

    public JSONArray covertToJsonArray(List list) {

        JSONArray ret = new JSONArray();
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            Object value = (Object) iterator.next();

            if (value instanceof Map) {
                JSONObject jsonObj = covertToJsonObj((Map) value);
                ret.put(jsonObj);
            }
            else {
                m_logger.info("**ERROR**: invalid format of list while converting to JSON array");
            }
        }

        return ret;
    }

    public static String beautify(String uglyJSONString) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJSONString);
        String prettyJsonString = gson.toJson(je);

        return prettyJsonString;
    }

    public static void main(String[] args) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        json.put("key", "key");

        json.putOpt("key2", "value");

        array.put(json);
        array.put(json);

        JSONObject top = new JSONObject();

        top.put("x", array);

        System.out.println(top.toString());
        System.out.println(array.toString());

    }

    private static Logger m_logger = Logger.getLogger(JsonUtil.class);
}
