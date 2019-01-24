/**
 * Project Name:springcloud-feign-consumer
 * File Name:FeignConsumerController.java
 * Package Name:org.springcloud.feign.consumer.controller
 * Date:2019年1月23日下午4:12:06
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.feign.consumer.controller;

import org.springcloud.feign.consumer.service.HelloService;
import org.springcloud.feign.consumer.service.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:FeignConsumerController <br/>
 * Function: 实现对 Feign 客户端的调用. <br/>
 * 	使用 @Autowired 直接注入上面定义的 HelloService 实例，并在 helloCosumer 函数中调用这个
 * 	绑定了 hello-service 服务接口的客户端来向该服务发起 /hello 接口的调用。
 * Date:     2019年1月23日 下午4:12:06 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@RestController
@RequestMapping("/feign-consumer")
public class FeignConsumerController {
	
	@Autowired
	private HelloService helloService;
	
	
	@RequestMapping(value="/hello", method=RequestMethod.GET)
	public String helloConsumer() {
		return helloService.hello();
	}
	
	@RequestMapping(value="/hello/params", method=RequestMethod.GET)
	public String helloConsumer2() {
		StringBuilder sb = new StringBuilder();
		sb.append(helloService.hello()).append("\n");
		sb.append(helloService.hello("kaiyun")).append("\n");
		sb.append(helloService.hello("kaiyun", 29)).append("\n");
		sb.append(helloService.hello(new User("kaiyun", 29))).append("\n");
		
		return sb.toString();
	}

}

