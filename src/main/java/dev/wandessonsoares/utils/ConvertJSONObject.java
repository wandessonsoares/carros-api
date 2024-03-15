package dev.wandessonsoares.utils;


import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConvertJSONObject {
    public JSONObject convert(int errorCode, String msg){
        JSONObject json = new JSONObject();
        json.put("message", msg);
        json.put("errorCode", errorCode);

        return json;
    }
}
