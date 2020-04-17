/**
 * Project Name:springcloud-feign-consumer
 * File Name:HelloService.java
 * Package Name:org.springcloud.feign.consumer.service
 * Date:2019年1月23日下午4:03:45
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.feign.consumer.service;

import org.springcloud.feign.consumer.configbean.FullLogConfiguration;
import org.springcloud.feign.consumer.dto.User;
import org.springcloud.feign.consumer.fallback.HelloServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ClassName:HelloService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2019年1月23日 下午4:03:45 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
/* 
 * 通过 @FeignClient 注解指定服务名来绑定服务，然后再使用 Spring MVC 的注解来绑定具体该服务提供的 REST 接口，这里服务名不区分大小写
 * 在初始化过程中，Spring Cloud Feign 会根据该注解的 name 属性或 value 属性指定的服务名，自动创建一个同名的 Ribbon 客户端。
 */
//@FeignClient("PROVIDER-SERVICE")
/* 
 * 针对某个服务客户端关闭 Hystrix 支持
 */
//@FeignClient(name="PROVIDER-SERVICE", configuration=DisableHystrixConfiguration.class)
/*
 * 通过 @FeignClient 注解的 fallback 属性来指定对应的服务降级实现类（注意：
 * 若之前有利用DisableHystrixConfiguration.class类关闭Hystrix支持时，这里需要将该类上的所有注解都去掉，服务降级才能起作用，否则不起作用）
 * 参考网站：https://blog.csdn.net/wangyuxuan_java/article/details/80588165
 */
//@FeignClient(name="PROVIDER-SERVICE", fallback=HelloServiceFallback.class)
/*
 * Feign 日志配置（通过实现配置类实现的）
 */

// 步骤一：通过 @FeignClient 注解指定服务名来绑定服务（服务名不区分小写）
@FeignClient(value="PROVIDER-SERVICE", fallback=HelloServiceFallback.class, configuration=FullLogConfiguration.class)
public interface HelloService {
	
	// 步骤二：使用 Spring MVC 的注解来绑定具体该服务提供的 REST 接口
	@RequestMapping(value="/hello")
	String hello();
	
//-----------------------------------------------------------------------------
//	Spring Cloud Feign 参数绑定：
//		在定义各参数绑定时，@RequestParam、@RequestHeader、@RequestBody等可以指定参数名称的注解，它们的 value 千万不能少。
//		在 SpringMVC 程序中，这些注解会根据参数名来作为默认值，
//		在 Feign 中绑定参数必须通过 value 属性来指明具体的参数名，不然会抛出 IllagalStateException 异常，value 属性不能为空；
//-----------------------------------------------------------------------------
	@RequestMapping(value="/hello1", method=RequestMethod.GET)
	String hello(@RequestParam("userName") String userName);
	
	@RequestMapping(value="/hello2", method=RequestMethod.GET)
	User hello(@RequestHeader("userName") String userName, @RequestHeader("age") Integer age);
	
	@RequestMapping(value="/hello3", method=RequestMethod.POST)
	String hello(@RequestBody User user);

}

