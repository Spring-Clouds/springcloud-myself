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
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
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
	 * AccessFilter:Spring Cloud Zuul：API网关服务-Zuul过滤器【或者在类AccessFilter上使用 @Component注解】. <br/>
	 * 		在实现自定义过滤器之后，它并不会直接生效，还需要为其创建具体的 Bean 才能启动该过滤器. <br/>
	 */
	@Bean
	public AccessFilter AccessFilter() {
		return new AccessFilter();
	}
	
	/**
	 * serviceRouteMapper:Spring Cloud Zuul：API网关服务-自定义路由映射规则. <br/>
	 * 	（1）在构建服务系统进行业务逻辑开发时，为了兼容外部不同版本的客户端程序，一般都会采用开闭原则来进行设计与开发。<br/>
	 * 	（2）需要为一组相互配合的微服务定义一个版本标识来方便管理它们的版本关系。<br/>
	 * 	（3）默认情况下，Zull 自动为服务创建的路由表达式会采用服务名作为前缀，比如：userService-v1,userService-v2，它会产生 /userService-v1, userService-v2 两个路径表达式来映射。<br/>
	 * 	（4）这样生成的表达式规则较为单一，不利于通过路径规则来进行管理。通常做法是为这些不同版本的微服务应用生成以版本代号作为路由前缀定义的路由规则。比如：/v1/userService/ <br/>
	 *	（5）自定义实现为上述规则的微服务自动化地创建类似 /v1/userService/**的路由匹配规则。<br/>
	 */
	@Bean
	public PatternServiceRouteMapper serviceRouteMapper() {
		// 第一个参数是：用来匹配服务名称是否符合该自定义规则的正则表达式
		// 第二个参数是：定义根据服务名中定义的内容转换出的路径表达式规则。
		// 只要符合第一个参数定义规则的服务名，都会优先使用该实现构建出的路径表达式，如果没有匹配的服务则还是会使用默认的路由映射规则。
		return new PatternServiceRouteMapper("(?<name>^.+)-(?<version>v.+$)", "${version}/${name}");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(APIGatewayApplication.class, args);
	}

}

