package com.xd.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class GsonUtil {
	static Gson gson = new GsonBuilder()
			.disableHtmlEscaping()
			.setDateFormat("yyyy-MM-dd HH:mm:ss") 
			.create();;    
	
	/*static{
		gson = new GsonBuilder()
		        .registerTypeAdapter(
		            new TypeToken<Map>(){}.getType(), 
		            new JsonDeserializer<Map>() {
		            @Override
		            public Map deserialize(
		            JsonElement json, Type typeOfT, 
		            JsonDeserializationContext context) throws JsonParseException {

		                Map map = new HashMap<>();
		                JsonObject jsonObject = json.getAsJsonObject();
		                Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
		                for (Map.Entry<String, JsonElement> entry : entrySet) {
		                	map.put(entry.getKey(), entry.getValue());
		                }
		                return map;
		            }
		        }).create();
	}*/
			
	/**
	 * 对象转json
	 * @param o
	 * @return
	 */
	public static String toJson(Object o){
		
		return gson.toJson(o);
	}
	public static String toJson(Object o, Type t){
		
		return gson.toJson(o,t);
	}
	
	/**
	 * json 转换为map
	 * @param json
	 * @return
	 */
	public static Map<String, Object> toMap(String json){
		@SuppressWarnings("unchecked")
		Map<String, Object> map = fromJson(json, Map.class);
		return map;
	}
	
	 /**
	  * 
	  * json转集合
     * @param json
     * @param clazz
     * @return
     */
    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz)
    {
        //Json的解析类对象
        JsonParser parser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray = parser.parse(json).getAsJsonArray();

        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonElement jsonObject : jsonArray)
        {
            arrayList.add(gson.fromJson(jsonObject, clazz));
        }
        return arrayList;
    }
    
   

	
	
	public static <T>T fromJson(String json, Class<T> classType){
		return gson.fromJson(json, classType);
		
	}
	public static <T>T fromJson(JsonArray jsonArray, Class<T> classType){
		return gson.fromJson(jsonArray, classType);
		
	}

	
	
	
}
