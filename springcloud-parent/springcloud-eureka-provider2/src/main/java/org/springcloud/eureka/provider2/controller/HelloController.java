/**
 * Project Name:springcloud-eureka-provider
 * File Name:HelloController.java
 * Package Name:org.springcloud.eureka.provider.controller
 * Date:2018年12月24日下午3:41:55
 * Copyright (c) 2018, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.eureka.provider2.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
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
	private DiscoveryClient discoveryClient;
	
	
	@RequestMapping(value="/hello")
	public String index() throws Exception {
		// 让处理线程等待几秒钟，模拟下服务阻塞
		//（由于Hystrix默认超时间是为1000毫秒，所以这里采用了0-3000的随机数以让处理过程有一定概率发生超时来触发断路器）
		int sleepTime = new Random().nextInt(3000);
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>sleepTime:{}", sleepTime);
		Thread.sleep(sleepTime);
		log.info(">>>>>>>>>>>>无 Request参数的请求：/hello");
		return "hello world2";
	}
	
}

