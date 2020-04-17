/**
 * Project Name:springcloud-api-gateway
 * File Name:HelloController.java
 * Package Name:org.springcloud.api.gateway.controller
 * Date:2019年1月30日下午4:10:37
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.api.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName:HelloController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2019年1月30日 下午4:10:37 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Slf4j
@RestController
public class HelloController {
	
	@Autowired
	private DiscoveryClient client;
	
	/**
	 * localHello:Spring Cloud Zuul：API网关服务-本地跳转. <br/>
	 *
	 */
	@RequestMapping(value="/local/hello")
	public String localHello() throws Exception {
		log.info(">>>>>>>>>>>>无 Request参数的请求：/local/hello");
		return "local hello world";
	}

}

