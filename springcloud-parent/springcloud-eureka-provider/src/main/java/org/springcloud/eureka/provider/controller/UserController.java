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

import javax.servlet.http.HttpServletResponse;

import org.springcloud.eureka.provider.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
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
	private DiscoveryClient discoveryClient;
	
	@RequestMapping(value="/users/findone/{id}", method=RequestMethod.GET)
	public User getUserById(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		User user = new User();
		user.setId(id);
		user.setUserName("kaiyun");
		log.info("/users/findone/{}", id);
		log.info("----------服务提供方 - getUserById 方法响应：{}", user.toString());
		return user;
	}
	
	/**
	 * getAllUser:(这里用一句话描述这个方法的作用). <br/>
	 *
	 * @param userIds
	 * @return
	 * @throws Exception
	 * @since JDK 1.8
	 * @author kaiyun
	 */
	@RequestMapping(value="/users/findall", method=RequestMethod.GET)
	public List<User> getAllUser(@RequestHeader(value="userIds") String userIds, HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		List<User> userList = new ArrayList<>();
		String[] userIdArray = userIds.split(",");
		for(String str : userIdArray) {
			User user = new User();
			user.setId(Long.valueOf(str));
			user.setUserName("kaiyun");
			userList.add(user);
		}
		log.info("/users/findall/{}", userIds);
		log.info("----------服务提供方 - getAllUser 方法响应：{}", userList.toString());
		return userList;
	}
}

