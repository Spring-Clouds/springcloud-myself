/**
 * Project Name:springcloud-config-client
 * File Name:ConfigClientApplication.java
 * Package Name:org.springcloud.config.client
 * Date:2019年2月1日下午1:54:46
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.config.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * ClassName:ConfigClientApplication <br/>
 * Function: 分布式配置中心 Spring Cloud Config：客户端（在微服务应用中获取配置中心的信息）. <br/>
 * Date:     2019年2月1日 下午1:54:46 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@SpringBootApplication
public class ConfigClientApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ConfigClientApplication.class, args);
	}

}

