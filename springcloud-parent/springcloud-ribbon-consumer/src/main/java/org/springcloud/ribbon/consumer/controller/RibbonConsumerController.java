/**
 * Project Name:springcloud-ribbon-consumer
 * File Name:RibbonConsumerController.java
 * Package Name:org.springcloud.ribbon.consumer.controller
 * Date:2018年12月26日上午9:56:19
 * Copyright (c) 2018, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.ribbon.consumer.controller;

import org.springcloud.ribbon.consumer.dto.User;
import org.springcloud.ribbon.consumer.service.HelloService;
import org.springcloud.ribbon.consumer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName:RibbonConsumerController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年12月26日 上午9:56:19 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Slf4j
@RestController
public class RibbonConsumerController {
	
	@Autowired
	private DiscoveryClient client;
	
//	@Autowired
//	private RestTemplate restTemplate;
	
	@Autowired
	private HelloService helloService;
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value="/ribbon-consumer", method=RequestMethod.GET)
	public String helloConsumer() {
//		return restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class).getBody();
		return helloService.hello();
	}
	
	@RequestMapping(value="/ribbon-consumer/users/sync/{id}", method=RequestMethod.GET)
	public String getUserByIdSyncUnAnnotations(@PathVariable("id") Long id) throws Exception {
		User user = userService.getUserByIdSyncUnAnnotations(id);
		
		ServiceInstance instance = client.getLocalServiceInstance();
		log.info("/ribbon-consumer/users/sync/{}, host:" + instance.getHost() + ", service_id:{}", id, instance.getServiceId());
		
		return user.toString();
	}
	
	@RequestMapping(value="/ribbon-consumer/users/async/{id}", method=RequestMethod.GET)
	public String getUserByIdAsyncUnAnnotations(@PathVariable("id") Long id) throws Exception {
		User user = userService.getUserByIdAsyncUnAnnotations(id).get();
		
		ServiceInstance instance = client.getLocalServiceInstance();
		log.info("/ribbon-consumer/users/async/{}, host:" + instance.getHost() + ", service_id:{}", id, instance.getServiceId());
		
		return user.toString();
	}
	
	@RequestMapping(value="/ribbon-consumer/users/sync/anno/{id}", method=RequestMethod.GET)
	public String getUserByIdSyncAnnotations(@PathVariable("id") Long id) throws Exception {
		User user = userService.getUserByIdSyncAnnotations(id);
		
		ServiceInstance instance = client.getLocalServiceInstance();
		log.info("/ribbon-consumer/users/sync/anno/{}, host:" + instance.getHost() + ", service_id:{}", id, instance.getServiceId());
		
		return user.toString();
	}
	
	@RequestMapping(value="/ribbon-consumer/users/async/anno/{id}", method=RequestMethod.GET)
	public String getUserByIdAsyncAnnotations(@PathVariable("id") Long id) throws Exception {
		User user = userService.getUserByIdAsyncAnnotations(id).get();
		
		ServiceInstance instance = client.getLocalServiceInstance();
		log.info("/ribbon-consumer/users/async/anno/{}, host:" + instance.getHost() + ", service_id:{}", id, instance.getServiceId());
		
		return user.toString();
	}

}

