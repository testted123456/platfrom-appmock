package com.platform.appmock.component.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ApiService {
	
	boolean support(HttpServletRequest request);
	
	void requestHandler(HttpServletRequest request, Map<String, String> map);
	
	void responseHandler(HttpServletResponse response, Map<String, String> map);
	
	void handler(HttpServletRequest request, HttpServletResponse response);
	
}
