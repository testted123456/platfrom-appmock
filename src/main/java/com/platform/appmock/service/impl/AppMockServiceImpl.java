package com.platform.appmock.service.impl;

import java.util.List;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.platform.appmock.entity.AppMockApi;
import com.platform.appmock.repository.AppMockApiRepository;
import com.platform.appmock.service.AppMockService;

@Service
public class AppMockServiceImpl implements AppMockService {
	
	public static Logger logger = LoggerFactory.getLogger(AppMockServiceImpl.class);

	@Autowired
	AppMockApiRepository appMockApiRepository;

	@Override
	public List<AppMockApi> getAll() {
		// TODO Auto-generated method stub
		return appMockApiRepository.findAll();
	}

	@Override
	public AppMockApi getById(Integer id) {
		// TODO Auto-generated method stub
		return appMockApiRepository.findById(id);
	}

	@Override
	public AppMockApi add(AppMockApi appMockApi) {
		// TODO Auto-generated method stub
		if(false == appMockApi.getType()){//新增目录
			appMockApi.setOptstatus((short)0);
			appMockApi = appMockApiRepository.save(appMockApi);
			return appMockApi;
		}
		
		String url = appMockApi.getUrl();
		
		AppMockApi aca = appMockApiRepository.findByUrl(url);
		
		if(null != aca){
			logger.warn("相同url：{}已存在...", url);
			return null;
		}
		
		appMockApi.setOptstatus((short)0);
		
		appMockApi = appMockApiRepository.save(appMockApi);
		return appMockApi;
	}

	@Override
	public AppMockApi update(AppMockApi appMockApi) {
		// TODO Auto-generated method stub
		Integer id = appMockApi.getId();
		
		if(null == id){//要更新的id没有传
			logger.warn("appMockApi id:{}没有传递...", id);
		}
		
		if(false == appMockApi.getType()){//新增目录
			appMockApi = appMockApiRepository.save(appMockApi);
			return appMockApi;
		}
		
		AppMockApi ama = appMockApiRepository.findById(id);
		
		if(null == ama){//数据库中没有对应的id
			logger.warn("appMockApi id:{} 不在系统中...", id);
			return null;
		}
		
		String url = appMockApi.getUrl();
		ama = appMockApiRepository.findByUrl(url);
		
		if(null == ama || ama.getId().equals(appMockApi.getId())){//url在数据库中没有重复的，或url没有改变
			appMockApi = appMockApiRepository.save(appMockApi);
			return appMockApi;
		}else{//更新的url和数据库中的其他url重复
			logger.warn("相同url：{}已存在...", url);
			return null;
		}
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		AppMockApi ama = appMockApiRepository.findById(id);
		
		if(null == ama){
			return 0;
		}else{
			ama.setOptstatus((short)2);
			appMockApiRepository.save(ama);
			return 1;
		}
	}
	
	/**
	 * 删除目录，同时删除目录下的节点
	 */
	@Override
	@Transactional
	public int deleteDirById(int id) {
		// TODO Auto-generated method stub
		List<AppMockApi> list = getByPId(id);
		
		for(AppMockApi ama : list){
			if(ama.getType() == true){
				deleteById(ama.getId());
			}else{
				deleteDirById(ama.getId());
			}
		}
		
		return 0;
	}

	@Override
	public List<AppMockApi> getByPId(Integer pId) {
		// TODO Auto-generated method stub
		List<AppMockApi> list = appMockApiRepository.findByPId(pId);
		return list;
	}
	

}
