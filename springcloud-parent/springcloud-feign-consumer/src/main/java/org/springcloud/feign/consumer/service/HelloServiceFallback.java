/**
 * Project Name:springcloud-feign-consumer
 * File Name:HelloServiceFallback.java
 * Package Name:org.springcloud.feign.consumer.service
 * Date:2019年1月25日下午1:56:47
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.feign.consumer.service;

import org.springcloud.feign.consumer.service.dto.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ClassName:HelloServiceFallback <br/>
 * Function: 服务降级类. <br/>
 * Date:     2019年1月25日 下午1:56:47 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Component
public class HelloServiceFallback implements HelloService {
	
	@Override
	public String hello() {
		return "error";
	}

	@Override
	public String hello(@RequestParam("userName") String userName) {
		return "error";
	}

	@Override
	public User hello(@RequestHeader("userName") String userName, @RequestHeader("age") Integer age) {
		return new User("未知", 0);
	}

	@Override
	public String hello(@RequestBody User user) {
		return "error";
	}


}

