/**
 * Project Name:springcloud-ribbon-consumer
 * File Name:RibbonConsumerController.java
 * Package Name:org.springcloud.ribbon.consumer.controller
 * Date:2018年12月26日上午9:56:19
 * Copyright (c) 2018, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.ribbon.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * ClassName:RibbonConsumerController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年12月26日 上午9:56:19 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@RestController
public class RibbonConsumerController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping(value="/ribbo-consumer", method=RequestMethod.GET)
	public String helloConsumer() {
		return restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class).getBody();
	}

}

