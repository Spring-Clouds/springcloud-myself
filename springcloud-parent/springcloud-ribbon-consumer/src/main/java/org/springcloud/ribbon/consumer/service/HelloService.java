/**
 * Project Name:springcloud-ribbon-consumer
 * File Name:HelloService.java
 * Package Name:org.springcloud.ribbon.consumer.service
 * Date:2019年1月9日下午6:55:33
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.ribbon.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName:HelloService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2019年1月9日 下午6:55:33 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Slf4j
@Service
public class HelloService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	// @HystrixComand 注解是Hystrix中的核心注解，通过它创建了 HystrixComand 的实现，同时利用 fallback 属性指定了服务降级的实现方法。
	// @HystrixComand 注解实现请求的同步执行，若要实现异步执行则需要另外定义。
	// 通过继承的方式来实现，实现 HystrixCommand 类。即可实现请求的同步执行也可以实现异步执行。（详细见5.3.1）
	// 测试限流的两个属性值：threadPoolKey 和 threadPoolProperties
	@HystrixCommand(fallbackMethod = "helloFallback", threadPoolKey="hello",
			threadPoolProperties={@HystrixProperty(name="coreSize", value="3")})
	public String hello() {
		System.out.println("----调用了----");
		long start = System.currentTimeMillis();
		String result = restTemplate.getForEntity("http://PROVIDER-SERVICE/hello", String.class).getBody();
		long end = System.currentTimeMillis();
		log.info(">>>>>>>>>>>>Spend time:{}", (end-start));
		
		return result.toString(); 
	}
	
	/**
	 * helloFallback:服务降级的方法（是Hystrix命令执行失败时使用的后备方法，用来实现服务的降级处理逻辑）. <br/>
	 *
	 * @return
	 * @since JDK 1.8
	 * @author kaiyun
	 */
	public String helloFallback() {
		return "error(由于Hystrix默认超时间是为1000毫秒，这里我们自己在配置文件中了2000毫秒。所以这里采用了0-3000的随机数以让处理过程有一定概率发生超时来触发断路器)";
	}

}

