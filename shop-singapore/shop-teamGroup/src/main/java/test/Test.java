package test;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

public class Test {
	
    
    public static void main(String[] args) {
    	String str = "{'person':[{'name':'huangbiao','age':15},{'name':'liumei','age':14}]}";
    	JSON jsonStr = JSON.parseObject(str);
    	Map<String,Object> map = JSONObject.toJavaObject(jsonStr,Map.class);
    	String personStr = map.get("person").toString();
    	List<Person> personList = JSONObject.parseObject(personStr,new TypeReference<List<Person>>(){});
    	System.out.println("--------------");
	}

}
