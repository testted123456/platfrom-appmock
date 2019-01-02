package com.platform.appmock.httpmsg;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component
public class HttpReqBody {
	
	public Map<String, String> getReqBodyOfMap(HttpServletRequest request){
		String contentType = request.getContentType();
		Map<String, String> map = new HashMap<String, String>();
		
		if(contentType.contains("application/x-www-form-urlencoded")){
			Enumeration<String> params = request.getParameterNames();
			
			while(params.hasMoreElements()){
				String param = params.nextElement();
				String val = request.getParameter(param);
				map.put(param, val);
			}
			
			return map;
		}
		
		return null;
	}
	
	public String getReqBodyString(HttpServletRequest request){
		String contentType = request.getContentType();
		
		if(null != contentType && contentType.contains("text/plain")){
			BufferedReader br = null;
			
			try {
				br = request.getReader();
				
				String str = "";
				StringBuilder sb = new StringBuilder(str);
				
				while((str = br.readLine()) != null){
					sb.append(str);
				}
				
				return sb.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public JSON getReqBodyJson(HttpServletRequest request){
		String contentType = request.getContentType();
		
		if(null != contentType && contentType.contains("text/json")){
			BufferedReader br = null;
			
			try {
				br = request.getReader();
				
				String str = "";
				StringBuilder sb = new StringBuilder(str);
				
				while((str = br.readLine()) != null){
					sb.append(str);
				}
				
				if(sb.length()>0){
					if(sb.toString().startsWith("{")){
						return JSONObject.parseObject(sb.toString()); 
					}else{
						return JSONArray.parseArray(sb.toString());
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}

	/**
	 * 以string或json格式的string返回请求消息体
	 * @param request
	 * @return
	 */
	public String getReqBody(HttpServletRequest request){
		
		String contentType = request.getContentType();
		
		if(null == contentType){
			return null;
		}
		
		if(contentType.contains("application/json") || contentType.contains("text/plain")){
			BufferedReader br = null;
			
			try {
				br = request.getReader();
				
				String str = "";
				StringBuilder sb = new StringBuilder(str);
				
				while((str = br.readLine()) != null){
					sb.append(str);
				}
				
				return sb.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(contentType.contains("application/x-www-form-urlencoded")){
			Enumeration<String> params = request.getParameterNames();
			
			JSONObject jsonObj = new JSONObject();
			
			while(params.hasMoreElements()){
				String param = params.nextElement();
				String val = request.getParameter(param);
				jsonObj.put(param, val);
			}
			
			return jsonObj.toJSONString();
		}
		
		return null;
	}
}
