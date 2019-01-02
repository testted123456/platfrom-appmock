package com.platform.appmock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.appmock.component.ApplicationContextProvider;
import com.platform.appmock.component.handler.ApiService;
import com.platform.appmock.component.result.Result;
import com.platform.appmock.component.result.ResultCode;
import com.platform.appmock.component.result.ResultUtil;
import com.platform.appmock.entity.AppMockApi;
import com.platform.appmock.httpmsg.HttpReqBody;
import com.platform.appmock.service.AppMockService;
import com.platform.appmock.utils.ClassUtil;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class AppMockController {
	
	public static Logger logger = LoggerFactory.getLogger(AppMockController.class);

	@Autowired
	HttpReqBody httpReqBody;
	
	@Autowired
	ApplicationContextProvider applicationContextProvider;
	
	@Autowired
	AppMockService appMockService;
	
	/**
	 * 处理所有请求
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/appMock/**")
	public void handle(HttpServletRequest request, HttpServletResponse response){
		logger.info("开始处理请求...");
		Map<String, ApiService>  beans = applicationContextProvider.getBeansOfType(ApiService.class);
		Optional<ApiService> apiService = beans.values().stream().filter(x->{return x.support(request);}).findFirst();
	
		if(apiService.isPresent() == false){
			logger.error("找不到处理请求的service...");
			return;
		}
	
		Map<String, String> map = new HashMap<String, String>();
		apiService.get().requestHandler(request, map);
		apiService.get().responseHandler(response, map);
		
		logger.info("处理请求结束...");
	}
	
	/**
	 * 获取处理器
	 * @return
	 */
	@ResponseBody
	@GetMapping(value="getApiHandlers")
	public Result getApiHandlers(){
		logger.info("开始获取api处理器...");
		List<Class<?>> clazzes = ClassUtil.getClasses("com.platform.appmock.component.handler.impl");
		
		List<String> list = clazzes.stream().map(x->{
			String name = x.getSimpleName();
			String [] handlers = name.split("ApiServiceImpl");
			return handlers[0];
		}).filter(x->{return !"AppMockServiceImpl".equals(x);})
				.collect(Collectors.toList());
		
		return ResultUtil.success(list);
	}
	
	/**
	 * 获取所有app mock
	 * @return 返回所有app mock列表
	 */
	@ResponseBody
	@GetMapping(value="getAll")
	public Result getAll(){
		logger.info("开始获取所有appMockApi...");
		List<AppMockApi> list = appMockService.getAll();
		return ResultUtil.success(list);
	}
	
	/**
	 * 新增app mock
	 * @param appMockApi
	 * @return
	 */
	@ResponseBody
	@PostMapping(value="add")
	public Result add(@RequestBody AppMockApi appMockApi){
		logger.info("开始新增appMockApi...");
		appMockApi = appMockService.add(appMockApi);
		
		if(null == appMockApi){
			return ResultUtil.error(ResultCode.EMPTY_ERROR.getCode(), "新增失败，url已存在！");
		}else{
			return ResultUtil.success(appMockApi);
		}
	}
	
	@ResponseBody
	@PostMapping(value="update")
	public Result update(@RequestBody AppMockApi appMockApi){
		logger.info("开始跟新appMockApi...");
		appMockApi = appMockService.update(appMockApi);
		
		if(null == appMockApi){
			return ResultUtil.error(ResultCode.EMPTY_ERROR.getCode(), "跟新失败，url已存在！");
		}else{
			return ResultUtil.success(appMockApi);
		}
	}
	
	/**
	 * 根据id查询app mock
	 * @param id
	 * @return 
	 */
	@ResponseBody
	@GetMapping(value="getById")
	public Result getById(@RequestParam Integer id){
		logger.info("开始查找appMockApi，id：{}", id);
		AppMockApi appMockApi = appMockService.getById(id);
		return ResultUtil.success(appMockApi);
	}
	
	/**
	 * 根据pId查找子节点
	 * @param pId
	 * @return
	 */
	@ResponseBody
	@GetMapping(value="getByPId")
	public Result getByPId(@RequestParam Integer pId){
		logger.info("开始查找appMockApi，pId：{}", pId);
		List<AppMockApi> list = appMockService.getByPId(pId);
		return ResultUtil.success(list);
	}
	
	/**
	 * 根据id来删除app mock
	 * @param id
	 * @return
	 */
	@ResponseBody
	@GetMapping(value="delById")
	public Result deleteById(@RequestParam Integer id){
		logger.info("开始删除appMockApi，id：{}", id);
		appMockService.deleteById(id);
		return ResultUtil.success();
	}
	
	/**
	 * 根据id删除目录，并删除目录下的节点
	 * @param id
	 * @return
	 */
	@ResponseBody
	@GetMapping(value="delDirById")
	public Result delDirById(@RequestParam Integer id){
		logger.info("开始删除appMockApi目录，id：{}", id);
		appMockService.deleteDirById(id);
		return ResultUtil.success();
	}
	
}
