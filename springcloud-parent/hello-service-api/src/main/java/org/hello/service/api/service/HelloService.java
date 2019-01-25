/**
 * Project Name:hello-service-api
 * File Name:HelloService.java
 * Package Name:org.hello.service.api.service
 * Date:2019年1月23日下午4:03:45
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.hello.service.api.service;

import org.hello.service.api.dto.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ClassName:HelloService <br/>
 * Function: 【hello-service-api】同时复用于服务端与客户端的接口. <br/>
 * Date:     2019年1月23日 下午4:03:45 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@RequestMapping("/refactor")
public interface HelloService {
	
	@RequestMapping(value="/hello4", method=RequestMethod.GET)
	public String hello(@RequestParam("userName") String userName);
	
	@RequestMapping(value="/hello5", method=RequestMethod.GET)
	public User hello(@RequestHeader("userName") String userName, @RequestHeader("age") Integer age);
	
	@RequestMapping(value="/hello6", method=RequestMethod.POST)
	public String hello(@RequestBody User user);

}

