/**
 * Project Name:springcloud-feign-consumer
 * File Name:DisableHystrixConfiguration.java
 * Package Name:org.springcloud.feign.consumer.configbean
 * Date:2019年1月25日下午1:37:15
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.feign.consumer.configbean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import feign.Feign;

/**
 * ClassName:DisableHystrixConfiguration <br/>
 * Function: 关闭 Hystrix 的配置类. <br/>
 * Date:     2019年1月25日 下午1:37:15 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Configuration
public class DisableHystrixConfiguration {
	
	/**
	 * feignBuilder: 若不想全局地关闭 Hystrix 支持，而只想针对某个服务客户端关闭 Hystrix 支持时，
	 * 		需要通过使用 @Scope("prototype")注解为指定的客户端 Feign.Builder 实例. <br/>
	 *
	 * @return
	 * @since JDK 1.8
	 * @author kaiyun
	 */
	@Bean
	@Scope("prototype")
	public Feign.Builder feignBuilder() {
		return Feign.builder();
	}
}

