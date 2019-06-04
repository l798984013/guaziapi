package com.mianze.apiexport.util;

import com.google.gson.*;

import java.lang.reflect.Type;


public class GsonUtil {
    private static Gson gson =
            new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .disableHtmlEscaping()
                    .registerTypeHierarchyAdapter(byte[].class, new ByteArrayTypeAdapter())
                    .create();

    public static Gson gson() {
        return gson;
    }

    private static final class ByteArrayTypeAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {

        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {

            String base64 = Base64.encodeToString(src, Base64.DEFAULT);
            return new JsonPrimitive(base64);
        }

        public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (!(json instanceof JsonPrimitive)) {
                throw new JsonParseException("The date should be a string value");
            }
            try {
                return Base64.decode(json.getAsString(), Base64.DEFAULT);
            } catch (Exception ignored) {
            }
            return null;
        }
    }

    private GsonUtil() {
    }

    public static String toString(Object object) {
        return gson.toJson(object);
    }

    public static <T> T toObject(String string, Class<T> cls) {
        return gson.fromJson(string, cls);
    }

}