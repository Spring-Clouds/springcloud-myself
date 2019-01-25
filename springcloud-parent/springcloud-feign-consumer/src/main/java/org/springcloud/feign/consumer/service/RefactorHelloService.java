/**
 * Project Name:springcloud-feign-consumer
 * File Name:RefactorHelloService.java
 * Package Name:org.springcloud.feign.consumer.service
 * Date:2019年1月24日下午6:13:37
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.feign.consumer.service;

import org.hello.service.api.service.HelloService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * ClassName:RefactorHelloService <br/>
 * Function: 【服务消费者-service】声明式服务调用 Spring Cloud Feign：继承特性（依赖hello-service-api服务模块）. <br/>
 * Date:     2019年1月24日 下午6:13:37 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
//通过 @FeignClient 注解指定服务名来绑定服务，然后再使用 Spring MVC 的注解来绑定具体该服务提供的 REST 接口，这里服务名不区分大小写
@FeignClient("PROVIDER-SERVICE")
public interface RefactorHelloService extends HelloService {
	
}

