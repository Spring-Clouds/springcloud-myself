/**
 * Project Name:springcloud-eureka-provider
 * File Name:HelloController.java
 * Package Name:org.springcloud.eureka.provider.controller
 * Date:2018年12月24日下午3:41:55
 * Copyright (c) 2018, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.eureka.provider.controller;

import org.hello.service.api.dto.User;
import org.hello.service.api.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName:HelloController <br/>
 * Function: 【服务提供者-controller】声明式服务调用 Spring Cloud Feign：继承特性（依赖hello-service-api服务模块）. <br/>
 * 	（1）继承 hello-service-api 模块中定义的 HelloService 接口。<br/>
 * 	（2）在 Controller 中不再包含以往会定义的请求映射注解 @RequestMapping，而参数的注解定义在重写时会自动带过来
 * Date:     2018年12月24日 下午3:41:55 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Slf4j
@RestController
public class RefactorHelloController implements HelloService {
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Override
	public String hello(@RequestParam("userName") String userName) {
		log.info(">>>>>>>>>>>>带有 Request参数的请求：/hello4");
		return "hello " + userName;
	}

	@Override
	public User hello(@RequestHeader("userName") String userName, @RequestHeader("age") Integer age) {
		log.info(">>>>>>>>>>>>带有Header信息的请求：/hello5");
		return new User(userName,age);
	}

	@Override
	public String hello(@RequestBody User user) {
		log.info(">>>>>>>>>>>>带有RequestBody的请求：/hello6");
		return "hello " + user.getUserName() + ", " + user.getAge();
	}
	
	
	
}

