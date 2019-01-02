package com.platform.appmock.service;

import java.util.List;
import com.platform.appmock.entity.AppMockApi;

public interface AppMockService {

	public List<AppMockApi> getAll();
	
	public List<AppMockApi> getByPId(Integer pId);
	
	public AppMockApi getById(Integer id);
	
	public AppMockApi add(AppMockApi appMockApi);
	
	public AppMockApi update(AppMockApi appMockApi);
	
	public int deleteById(int id);
	
	public int deleteDirById(int id);
}
