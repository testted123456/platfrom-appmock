package com.platform.appmock.component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.platform.appmock.entity.AppMockApi;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {
	
	 private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}
	
	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}
	
	public Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}
	
	public <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}
	
	public <T> T getBean(String name, Class<T> clazz) {
		return getApplicationContext().getBean(name, clazz);
	}
	
	public <T> Map<String, T> getBeansOfType(Class<T> clazz){
		return getApplicationContext().getBeansOfType(clazz);
	}
	
}
