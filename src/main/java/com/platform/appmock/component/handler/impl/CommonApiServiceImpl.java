package com.platform.appmock.component.handler.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.platform.appmock.component.handler.ApiService;
import com.platform.appmock.entity.AppMockApi;
import com.platform.appmock.httpmsg.HttpReqBody;
import com.platform.appmock.httpmsg.HttpReqHeader;
import com.platform.appmock.httpmsg.HttpRespBody;
import com.platform.appmock.httpmsg.HttpRespHeader;
import com.platform.appmock.repository.AppMockApiRepository;
import com.platform.appmock.utils.DBUtils;
import com.platform.appmock.utils.SignatureUtils;

@Service
public class CommonApiServiceImpl implements ApiService {
	public static Logger logger = LoggerFactory.getLogger(CommonApiServiceImpl.class);

	@Autowired
	HttpReqHeader httpReqHeader;
	
	@Autowired
	HttpReqBody httpReqBody;
	
	@Autowired
	HttpRespHeader httpRespHeader;
	
	@Autowired
	HttpRespBody httpRespBody;
	
	@Autowired
	AppMockApiRepository appMockApiRepository;
	
	@Value("${xinxiang.datasource.driver}")
	String dbDriver;
	
	@Value("${xinxiang.datasource.url}")
	String dburl;
	
	@Value("${xinxiang.datasource.username}")
	String dbUserName;
	
	@Value("${xinxiang.datasource.password}")
	String dbPassword;
//	String signKey = "n85o3nd2romjnb06onivuq1m85k1qnfl";
	
	@Override
	public boolean support(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String url = request.getRequestURI();
		AppMockApi appMockApi = appMockApiRepository.findByUrl(url);
		String handler = appMockApi.getHandler();
		
		if("Common".equals(handler)){
			return true;
		}
		
		return false;
	}

	@Override
	public void requestHandler(HttpServletRequest request, Map<String, String> map) {
		// TODO Auto-generated method stub
		String contentType = request.getContentType();
		String requestUrl = request.getRequestURI();
		
		if(null == contentType){
			return;
		}
		
		if(contentType.contains("application/json")){
			
		}
		
		map.put("requestUrl", requestUrl);
		
		if(contentType.contains("application/x-www-form-urlencoded")){
			String reqXML = httpReqBody.getReqBodyOfMap(request).get("requestXml");
			JSONObject reqOfJson = JSONObject.parseObject(reqXML);
			String clientType = reqOfJson.getString("clientType");
			String version = reqOfJson.getString("version");
			String appUser = reqOfJson.getString("appUser");
			
			map.put("clientType", clientType);
			map.put("version", version);
			map.put("appUser", appUser);
		}
	}

	@Override
	public void responseHandler(HttpServletResponse response, Map<String, String> map) {
		// TODO Auto-generated method stub
		String url = map.get("requestUrl");
		String clientType = map.get("clientType");
		String version = map.get("version");
		String appUser = map.get("appUser");
		
		AppMockApi appMockApi = appMockApiRepository.findByUrl(url);
		String header = appMockApi.getResponseHead();
		String contentType = httpRespHeader.getContentType(header);
		String charSet = null;
		
		String [] cts = contentType.split(";");
		
		for(String ct : cts){
			if(ct.contains("charset")){
				String [] cs = ct.split("=");
				if(cs.length > 1){
					charSet = cs[1];
					break;
				}
			}
		}
		
		response.setContentType(contentType);
		
		//设置响应编码
		if(null == charSet){
			response.setCharacterEncoding("UTF-8");
		}else{
			response.setCharacterEncoding(charSet);
		}
		
		String respBody = appMockApi.getResponseBody();
		
		if(contentType.contains("application/json")){
			logger.info("响应消息是json格式...");
			
			JSONObject respBodyOfJson = JSONObject.parseObject(respBody);
			boolean isSignature = respBodyOfJson.getBooleanValue("sign");
			
			if(true == isSignature){//加签
				String signKey = null;
				try {
					Connection con = DBUtils.getConnection(dbDriver, 
								dburl, dbUserName, dbPassword);
					String sql = "select key"
				    		+ " from prep_client_version where client_type='" + clientType + "'"
				    		+ " and client_version='" + version + "'"
				    		+ " and appuser='" + appUser + "'";
				    Object key = DBUtils.getOneObject(con, sql);
				    signKey = String.valueOf(key);
				    logger.info("加签密钥：{}", signKey);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				    
				respBodyOfJson.put("sign", signKey);
			    
				try {
					String newSign =  SignatureUtils.md5(URLEncoder.encode(respBodyOfJson.toJSONString(), "UTF-8")).toUpperCase();
					respBodyOfJson.put("sign", newSign);
					httpRespBody.set(header, respBodyOfJson.toJSONString(), response);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}else{//不加签
				logger.info("响应消息不加签...");
				httpRespBody.set(header, respBody, response);
			}

			return;
		}
		
		if(contentType.contains("text/plain")){
		}
		
		if(contentType.contains("application/x-www-form-urlencoded")){
		}
	}

	@Override
	public void handler(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

}
