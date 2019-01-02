package com.platform.appmock.httpmsg;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component
public class HttpRespBody {

	public boolean set(String header, String body, HttpServletResponse response){
		
		JSONArray jsonArr = JSONArray.parseArray(header);
		
		int size = jsonArr.size();
		
		String contentType = null;
		
		for(int i=0;i<size;i++){
			JSONObject jsonObj = jsonArr.getJSONObject(i);
			
			String key = jsonObj.getString("Key");
			
			if(key.equals("Content-Type")){
				String name = jsonObj.getString("Value");
				contentType = name;
				break;
			}
		}
		
		if(null == contentType){
			return false;
		}
		
		PrintWriter pw = null;
		
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(contentType.contains("application/json")){
			pw.write(body);
			pw.flush();
			pw.close();
			return true;
		}
		
		if(contentType.contains("application/x-www-form-urlencoded")){
			pw.write(body);
			pw.flush();
			pw.close();
			return true;
		}
		
		if(contentType.contains("text/plain")){
//			JSONObject jsonObj = JSONObject.parseObject(body);
//			String respBody = jsonObj.getString("response");
			pw.write(body);
			pw.flush();
			pw.close();
			return true;
		}
		
		return false;
	}
	
}
