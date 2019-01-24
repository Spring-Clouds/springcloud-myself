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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName:HelloController <br/>
 * Function: 声明式服务调用 Spring Cloud Feign：继承特性【依赖hello-service-api服务模块】. <br/>
 * Date:     2018年12月24日 下午3:41:55 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Slf4j
@RestController
public class RefactorHelloController implements HelloService {
	
	@Override
	public String hello(@RequestParam("userName") String userName) {
		return "hello " + userName;
	}

	@Override
	public User hello(@RequestHeader("userName") String userName, @RequestHeader("age") Integer age) {
		return new User(userName,age);
	}

	@Override
	public String hello(@RequestBody User user) {
		return "hello " + user.getUserName() + ", " + user.getAge();
	}
	
	
	
}

