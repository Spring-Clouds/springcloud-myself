/**
 * Project Name:api-gateway-dynamic-filter
 * File Name:APIGatewayDynamicFilterApplication.java
 * Package Name:org.api.gateway.dynamic.filter
 * Date:2019年1月31日下午3:14:21
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.api.gateway.dynamic.filter;

import org.api.gateway.dynamic.filter.config.FilterConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;

/**
 * ClassName:APIGatewayDynamicFilterApplication <br/>
 * Function: Spring Cloud Zuul:API网关服务-动态过滤器. <br/>
 * Date:     2019年1月31日 下午3:14:21 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@EnableConfigurationProperties((FilterConfiguration.class))	// 引入自定义属性配置类
@EnableZuulProxy	// 开启 Zuul 的 API 网关服务功能
@SpringCloudApplication
public class APIGatewayDynamicFilterApplication {
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(APIGatewayDynamicFilterApplication.class).web(true).run(args);
	}
	
	/**
	 * filterLoader: 创建动态加载过滤器的实例. <br/>
	 */
	@Bean
	public FilterLoader filterLoader(FilterConfiguration filterConfiguration) {
		FilterLoader filterLoader = FilterLoader.getInstance();
		filterLoader.setCompiler(new GroovyCompiler());
		try {
			FilterFileManager.setFilenameFilter(new GroovyFileFilter());
			FilterFileManager.init(
					filterConfiguration.getInterval(), 
					filterConfiguration.getRoot() + "/pre", 
					filterConfiguration.getRoot() + "/post");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return filterLoader;
	}

}

