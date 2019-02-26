package com.zp.android.zlib.http;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpParam {

    Map<String, String> map = new LinkedHashMap<>();

    HttpParam() { }

    @Override
    public String toString() {
        if (map.keySet().size() == 0) {
            return super.toString();
        }
        else {
            StringBuilder sb = new StringBuilder();
            for (String key : map.keySet()) {
                sb.append(key).append("=").append(map.get(key)).append("&");
            }
            return sb.toString();
        }
    }

    Map<String, String> getParams() {
        return map;
    }

    public static class Factory {

        private HttpParam param = new HttpParam();

        public Factory() { }

        public Factory add(String key, String value) {
            param.map.put(key, value);
            return this;
        }

        public HttpParam create() {
            return param;
        }
    }
}
