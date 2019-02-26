package pers.zjc.sams.app;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.zp.android.zlib.utils.StringUtils;
import com.zp.android.zlib.utils.TimeUtils;

import java.lang.reflect.Type;
import java.util.Date;

public class DateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement json, Type typeOfT,
                            JsonDeserializationContext context) throws JsonParseException {

        if (json == null || StringUtils.isEmpty(json.getAsString())) {
            return null;
        }
        else {
            try {
                return TimeUtils.string2Date(json.getAsString());
            }
            catch (Exception e) {
                return null;
            }
        }
    }

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {

        if (src == null) {
            return null;
        }
        return new JsonPrimitive(TimeUtils.date2String(src));
    }
}
