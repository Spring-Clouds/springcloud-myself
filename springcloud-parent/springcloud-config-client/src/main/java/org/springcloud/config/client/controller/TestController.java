/**
 * Project Name:springcloud-config-client
 * File Name:TestController.java
 * Package Name:org.springcloud.config.client.controller
 * Date:2019年2月1日下午2:38:55
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.config.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:TestController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2019年2月1日 下午2:38:55 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@RefreshScope
@RestController
public class TestController {
	
	//------------------ 以下通过 @Value 注解绑定注入
	
	@Value("${from}")
	private String from;
	
	@RequestMapping("/from")
	public String from() {
		return this.from;
	}
	
	//------------------ 以下是通过 Environment 对象来获取配置属性
	
	@Autowired
	private Environment env;
	
	@RequestMapping("/from2")
	public String from2() {
		return env.getProperty("from", "undefind");
	}

}

