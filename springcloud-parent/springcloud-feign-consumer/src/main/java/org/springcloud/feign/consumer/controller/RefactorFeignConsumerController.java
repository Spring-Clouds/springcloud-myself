/**
 * Project Name:springcloud-feign-consumer
 * File Name:FeignConsumerController.java
 * Package Name:org.springcloud.feign.consumer.controller
 * Date:2019年1月23日下午4:12:06
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.feign.consumer.controller;

import org.hello.service.api.dto.User;
import org.springcloud.feign.consumer.service.RefactorHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:RefactorFeignConsumerController <br/>
 * Function: 实现对 Feign 客户端的调用. <br/>
 * Date:     2019年1月23日 下午4:12:06 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@RestController
@RequestMapping("/feign-consumer")
public class RefactorFeignConsumerController {
	
	@Autowired
	private RefactorHelloService refactorHelloService;
	
	
	@RequestMapping(value="/hello/params/extends", method=RequestMethod.GET)
	public String helloConsumer2() {
		StringBuilder sb = new StringBuilder();
		sb.append(refactorHelloService.hello("kaiyun")).append("\n");
		sb.append(refactorHelloService.hello("kaiyun", 29)).append("\n");
		sb.append(refactorHelloService.hello(new User("kaiyun", 29))).append("\n");
		
		return sb.toString();
	}

}

