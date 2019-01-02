package com.platform.appmock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.platform.appmock.entity.AppMockApi;

@Repository
public interface AppMockApiRepository extends JpaRepository<AppMockApi, Integer> {

	AppMockApi findByUrl(String url);
	
	AppMockApi findById(Integer id);
	
	List<AppMockApi> findByPId(Integer pId);
}
