package com.platform.appmock.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AppMockApi {

	@Id
	@GeneratedValue
	Integer id;
	
	@Column(nullable=false, columnDefinition="varchar(300) COMMENT 'app mock名称'")
	String name;
	
	@Column(nullable=true, columnDefinition="varchar(1000) COMMENT '描述'")
	String description;
	
	@Column(nullable=true, columnDefinition="varchar(300) COMMENT 'app mock对应的url'")
	String url;
	
	@Column(nullable=true, columnDefinition="varchar(20) COMMENT '处理app mock的handler名称'")
	String handler;
	
	@Column(columnDefinition="char(1) COMMENT '0:Http;1:Https;'")
	Character apiProtocol; 
	
	@Column(columnDefinition="char(1) COMMENT '0:get，1:post'")
	Character apiSendWay;
	
	@Column(columnDefinition="varchar(255)")
	String requestHead;
	
	@Column(columnDefinition="text")
	String requestBody;
	
	@Column(columnDefinition="varchar(255)")
	String responseHead;
	
	@Column(columnDefinition="text")
	String responseBody;
	
	String createdBy;
	
	@Column(columnDefinition="datetime")
	LocalDateTime createdTime;
	
	String updatedBy;
	
	@Column(columnDefinition="datetime")
	LocalDateTime updatedTime;
	
	@Column(nullable=true, columnDefinition="bit(1) COMMENT '0:目录，1:非目录'")
	Boolean type;
	
	@Column(nullable=false, columnDefinition="bigint(20) COMMENT '父节点id'")
	Integer pId;
	
	@Column(nullable=false, columnDefinition="smallint(1) COMMENT '0:正常，1:已更新，2:已删除'")
	Short optstatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setType(Boolean type) {
		this.type = type;
	}
	
	public Boolean getType(){
		return type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public Character getApiProtocol() {
		return apiProtocol;
	}

	public void setApiProtocol(Character apiProtocol) {
		this.apiProtocol = apiProtocol;
	}

	public Character getApiSendWay() {
		return apiSendWay;
	}

	public void setApiSendWay(Character apiSendWay) {
		this.apiSendWay = apiSendWay;
	}

	public String getRequestHead() {
		return requestHead;
	}

	public void setRequestHead(String requestHead) {
		this.requestHead = requestHead;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public String getResponseHead() {
		return responseHead;
	}

	public void setResponseHead(String responseHead) {
		this.responseHead = responseHead;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedTime() {
		
		if(null != this.createdTime){
			return this.createdTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}else{
			return null;
		}
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}
	
	public void setCreatedTime(String createdTime){
		if(null != createdTime){
			this.createdTime = LocalDateTime.parse(createdTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	
    public String getUpdatedTime() {
		
		if(null != this.updatedTime){
			return this.updatedTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}else{
			return null;
		}
	}

	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}
	
	public void setUpdatedTime(String updatedTime){
		if(null != updatedTime){
			this.updatedTime = LocalDateTime.parse(updatedTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public Short getOptstatus() {
		return optstatus;
	}

	public void setOptstatus(Short optstatus) {
		this.optstatus = optstatus;
	}
}
