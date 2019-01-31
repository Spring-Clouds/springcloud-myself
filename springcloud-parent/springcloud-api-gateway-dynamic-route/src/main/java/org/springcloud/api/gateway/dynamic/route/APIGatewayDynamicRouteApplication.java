/**
 * Project Name:springcloud-api-gateway-dynamic-route
 * File Name:APIGatewayDynamicRoute.java
 * Package Name:org.springcloud.api.gateway.dynamic.route
 * Date:2019年1月31日下午2:55:40
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.api.gateway.dynamic.route;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;

/**
 * ClassName:APIGatewayDynamicRoute <br/>
 * Function: Spring Cloud Zuul：API网关服务-动态路由. <br/>
 * Date:     2019年1月31日 下午2:55:40 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@EnableZuulProxy	// 开启 Zuul 的 API 网关服务功能
@SpringCloudApplication
public class APIGatewayDynamicRouteApplication {
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(APIGatewayDynamicRouteApplication.class).web(true).run(args);
	}
	
	/**
	 * zuulProperties:使用 @RefreshScope 注解来将 Zuul 的配置内容动态化. <br/>
	 */
	@Bean
	@RefreshScope
	@ConfigurationProperties("zuul")
	public ZuulProperties zuulProperties() {
		return new ZuulProperties();
	}

}

