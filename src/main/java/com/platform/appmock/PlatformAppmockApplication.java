package com.platform.appmock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableAsync
@EnableRedisHttpSession
@EnableEurekaClient
@RefreshScope
@RestController
@Configuration
@EnableDiscoveryClient
@EnableFeignClients
public class PlatformAppmockApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlatformAppmockApplication.class, args);
	}

}

