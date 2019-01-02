package com.platform.appmock.httpmsg;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class HttpReqHeader {

	String getContentType(HttpServletRequest request){
		return request.getContentType();
	}
	
	String getUrl(HttpServletRequest request){
		return request.getRequestURI();
	}
	
	Enumeration<String> getRequestHeaders(HttpServletRequest request){
		return request.getHeaderNames();
	}
}
