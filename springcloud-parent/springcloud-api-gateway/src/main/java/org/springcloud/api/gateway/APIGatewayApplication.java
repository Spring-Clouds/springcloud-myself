/**
 * Project Name:springcloud-api-gateway
 * File Name:APIGatewayApplication.java
 * Package Name:org.springcloud.api.gateway
 * Date:2019年1月30日上午11:39:07
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.api.gateway;

import org.springcloud.api.gateway.filter.AccessFilter;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * ClassName:APIGatewayApplication <br/>
 * Function: Spring Cloud Zuul：API网关服务. <br/>
 * Date:     2019年1月30日 上午11:39:07 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@EnableZuulProxy	// 开启 Zuul 的 API 网关服务功能
@SpringCloudApplication
public class APIGatewayApplication {
	
	/**
	 * AccessFilter:Spring Cloud Zuul：API网关服务-Zuul过滤器. <br/>
	 * 		在实现自定义过滤器之后，它并不会直接生效，还需要为其创建具体的 Bean 才能启动该过滤器. <br/>
	 */
	@Bean
	public AccessFilter AccessFilter() {
		return new AccessFilter();
	}
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(APIGatewayApplication.class).web(true).run(args);
	}

}

