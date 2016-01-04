package pers.ksy.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GsonUtil {
    private Map<String, String> classMap = new HashMap<>();
    private Map<String, Map<String, String>> subClassMaps = new HashMap<>();

    public String genClassFromJson(String json) {
        JsonObject obj = getRootObj(json);
        addPropToMap(obj, classMap);
        return genClassString();
    }

    private JsonObject getRootObj(String json) {
        JsonElement jsonElement = new Gson().fromJson(json, JsonElement.class);
        if (jsonElement.isJsonArray()) {
            return (JsonObject) jsonElement.getAsJsonArray().get(0);
        } else {
            return (JsonObject) jsonElement;
        }
    }

    private void addPropToMap(JsonObject jsonObject, Map<String, String> map) {
        for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            if (entry.getValue().isJsonPrimitive()) {
                map.put(entry.getKey(), "String");
            } else if (entry.getValue().isJsonNull()) {
                map.put(entry.getKey(), "Object");
            } else {
                JsonObject object = null;
                if (entry.getValue().isJsonArray()) {
                    JsonArray array = entry.getValue().getAsJsonArray();
                    if (array.size() > 0) {
                        object = array.get(0).getAsJsonObject();
                    }
                } else {
                    object = entry.getValue().getAsJsonObject();
                }
                if(null!=object){
                    String type = entry.getKey().substring(0, 1)
                            .toUpperCase()
                            + entry.getKey().substring(1);
                    Map<String, String> m = new HashMap<>();
                    addPropToMap(object, m);
                    map.put(entry.getKey(), type);
                    subClassMaps.put(type, m);
                }else{
                    map.put(entry.getKey(),"Object");
                }
            }
        }
    }

    private String genClassString() {
        StringBuilder sb = new StringBuilder();
        sb.append("public class JsonBean {\n").append(
                genClassPropString(classMap));
        for (Entry<String, Map<String, String>> entry : subClassMaps.entrySet()) {
            sb.append("public static class ").append(entry.getKey())
                    .append(" {\n")
                    .append(genClassPropString(entry.getValue())).append("\n}");
        }
        sb.append("\n}");
        return sb.toString();
    }

    private String genClassPropString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> e : map.entrySet()) {
            sb.append("private ").append(e.getValue()).append(" ")
                    .append(e.getKey()).append(";\n");
        }
        return sb.toString();
    }
    
}
