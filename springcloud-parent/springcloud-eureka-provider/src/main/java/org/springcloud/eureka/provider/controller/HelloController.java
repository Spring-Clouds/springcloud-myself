/**
 * Project Name:springcloud-eureka-provider
 * File Name:HelloController.java
 * Package Name:org.springcloud.eureka.provider.controller
 * Date:2018年12月24日下午3:41:55
 * Copyright (c) 2018, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.eureka.provider.controller;

import java.util.Random;

import org.springcloud.eureka.provider.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName:HelloController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年12月24日 下午3:41:55 <br/>
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
	
	@RequestMapping(value="/hello")
	public String index() throws Exception {
		ServiceInstance instance = client.getLocalServiceInstance();
		
		// 让处理线程等待几秒钟，模拟下服务阻塞
		//（由于Hystrix默认超时间是为1000毫秒，所以这里采用了0-3000的随机数以让处理过程有一定概率发生超时来触发断路器）
		int sleepTime = new Random().nextInt(3000);
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>sleepTime:{}", sleepTime);
		Thread.sleep(sleepTime);
		log.info(">>>>>>>>>>>>无 Request参数的请求：/hello, host:" + instance.getHost() + ", service_id:" + instance.getServiceId());
		return "hello world";
	}
	
	
//-----------------------------------------------------------------------
//-------- Spring Cloud Feign 参数绑定：
//-------- （1）包含带有 Request参数的请求
//-------- （2）带有Header信息的请求
//-------- （3）带有RequestBody的请求，以及请求响应体中是一个对象的请求
//-----------------------------------------------------------------------
	@RequestMapping(value="/hello1", method=RequestMethod.GET)
	public String hello(@RequestParam String userName) throws Exception {
		ServiceInstance instance = client.getLocalServiceInstance();
		log.info(">>>>>>>>>>>>带有 Request参数的请求：/hello1, host:" + instance.getHost() + ", service_id:" + instance.getServiceId());
		return "hello " + userName;
	}
	
	@RequestMapping(value="/hello2", method=RequestMethod.GET)
	public User hello(@RequestHeader String userName, @RequestHeader Integer age) throws Exception {
		ServiceInstance instance = client.getLocalServiceInstance();
		log.info(">>>>>>>>>>>>带有Header信息的请求：/hello2, host:" + instance.getHost() + ", service_id:" + instance.getServiceId());
		return new User(userName,age);
	}
	
	@RequestMapping(value="/hello3", method=RequestMethod.POST)
	@ResponseBody
	public String hello(@RequestBody User user) throws Exception {
		ServiceInstance instance = client.getLocalServiceInstance();
		log.info(">>>>>>>>>>>>带有RequestBody的请求：/hello3, host:" + instance.getHost() + ", service_id:" + instance.getServiceId());
		return "hello " + user.getUserName() + ", " + user.getAge();
	}
	
}

