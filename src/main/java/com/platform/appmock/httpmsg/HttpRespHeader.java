package com.platform.appmock.httpmsg;

import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component
public class HttpRespHeader {
	
	public String getContentType(String header){
		JSONObject headerOfJson = parseHeaders(header);
		return headerOfJson.getString("Content-Type");
	}
	
	public JSONObject parseHeaders(String header){
		JSONArray jsonArr = JSONArray.parseArray(header);
		
		JSONObject jsonObj = new JSONObject();
		
        int size = jsonArr.size();
		
		for(int i=0;i<size;i++){
			JSONObject jo = jsonArr.getJSONObject(i);
			
			String key = jo.getString("Key");
			
			String value = jo.getString("Value");
			
			jsonObj.put(key, value);
		}
		
		return jsonObj;
	}

	public void set(JSONObject headerOfJson, HttpServletResponse response){
		headerOfJson.forEach((x,y)->{
			response.setHeader(x, String.valueOf(y));
		});
	}
}
