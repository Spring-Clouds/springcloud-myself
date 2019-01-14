/**
 * Project Name:springcloud-eureka-provider
 * File Name:HelloController.java
 * Package Name:org.springcloud.eureka.provider.controller
 * Date:2018年12月24日下午3:41:55
 * Copyright (c) 2018, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.eureka.provider.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springcloud.eureka.provider.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import lombok.extern.slf4j.Slf4j;

/**
 * ClassName:UserController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年12月24日 下午3:41:55 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Slf4j
@RestController
public class UserController {
	
	@Autowired
	private DiscoveryClient client;
	
	@RequestMapping(value="/users/{id}", method=RequestMethod.GET)
	public User getUserById(@PathVariable("id") Long id) throws Exception {
		ServiceInstance instance = client.getLocalServiceInstance();
		User user = new User();
		user.setId(id);
		user.setUserName("kaiyun");
		log.info("/users/{}, host:" + instance.getHost() + ", service_id:{}", id, instance.getServiceId());
		log.info("----------{}", user.toString());
		return user;
	}
	
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public List<User> getAllUser(String ids) throws Exception {
		ServiceInstance instance = client.getLocalServiceInstance();
		List<User> userList = new ArrayList<>();
		User user = new User();
		user.setId(1L);
		user.setUserName("kaiyun");
		userList.add(user);
		log.info("/users/{}, host:" + instance.getHost() + ", service_id:{}", ids, instance.getServiceId());
		log.info("----------{}", userList.toString());
		return userList;
	}
}

