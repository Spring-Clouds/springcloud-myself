/**
 * Project Name:springcloud-eureka-provider
 * File Name:HelloController.java
 * Package Name:org.springcloud.eureka.provider.controller
 * Date:2018年12月24日下午3:41:55
 * Copyright (c) 2018, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.eureka.provider.controller;

import java.util.List;
import java.util.Random;

import org.springcloud.eureka.provider.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName:HelloController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年12月24日 下午3:41:55 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Slf4j
@RestController
public class HelloController {
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	
	@RequestMapping(value="/hello")
	public String index() throws Exception {
		
		// 测试请求超时，让处理线程等待几秒钟，模拟下服务阻塞
		//（由于Hystrix默认超时间是为1000毫秒，所以这里采用了0-3000的随机数以让处理过程有一定概率发生超时来触发断路器）
//		int sleepTime = new Random().nextInt(3000);
//		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>sleepTime:{}", sleepTime);
//		Thread.sleep(sleepTime);
		
		
		
//		// 测试熔断,在规定的时间内，失败指定的次数，就熔断，不再执行方法调用
//		if(true) {
//			throw new Exception("人为所致错误，测试熔断！");
//		}
		
		
		// 测试限流，在消费方设置方法执行时间为：20秒，在提供方方法中sleep10秒，在10秒内请求线程达到设置指定的值，就不再执行消费方的方法
		int sleepTime = new Random().nextInt(1000*10);
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>sleepTime:{}", sleepTime);
		Thread.sleep(sleepTime);
		
		
		log.info(">>>>>>>>>>>>无 Request参数的请求：/hello");
		return "hello world";
	}
	
	
//-----------------------------------------------------------------------
//-------- Spring Cloud Feign 参数绑定：
//-------- （1）包含带有 Request参数的请求
//-------- （2）带有Header信息的请求
//-------- （3）带有RequestBody的请求，以及请求响应体中是一个对象的请求
//-----------------------------------------------------------------------
	@RequestMapping(value="/hello1", method=RequestMethod.GET)
	public String hello(@RequestParam String userName) throws Exception {
		log.info(">>>>>>>>>>>>带有 Request参数的请求：/hello1");
		return "hello " + userName;
	}
	
	@RequestMapping(value="/hello2", method=RequestMethod.GET)
	public User hello(@RequestHeader String userName, @RequestHeader Integer age) throws Exception {
		log.info(">>>>>>>>>>>>带有Header信息的请求：/hello2");
		return new User(userName,age);
	}
	
	@RequestMapping(value="/hello3", method=RequestMethod.POST)
	@ResponseBody
	public String hello(@RequestBody User user) throws Exception {
		log.info(">>>>>>>>>>>>带有RequestBody的请求：/hello3");
		return "hello " + user.getUserName() + ", " + user.getAge();
	}
	
	@ResponseBody
    @GetMapping("/getServices")
    public List<String> getServices() {
        List<String> list = discoveryClient.getServices();
        System.out.println("------------------------------------");
        if (list != null && list.size() > 0) {
            for (String serviceId : list) {
                System.out.println("服务Id : " + serviceId);
            }
        } else {
            System.out.println("注册中心无服务实例");
        }

        System.out.println("------------------------------------");
        return list;
    }
	
	@ResponseBody
    @GetMapping("/getInstances/{serviceId}")
    public List<ServiceInstance> getInstances(@PathVariable("serviceId") String serviceId) {

        List<ServiceInstance> list = discoveryClient.getInstances(serviceId);

        System.out.println("------------------------------------");
        if (list != null && list.size() > 0) {
            for (ServiceInstance instance : list) {
                System.out.println("");
                System.out.println("******************************");
                System.out.println("服务实例信息：");
                System.out.println("服务 ServiceId：" + instance.getServiceId());
                System.out.println("服务 Host：" + instance.getHost());
                System.out.println("服务 Port：" + instance.getPort());
                System.out.println("服务 Uri：" + instance.getUri().toString());
                System.out.println("服务 Metadata：" + instance.getMetadata().toString());
                System.out.println("******************************");
                System.out.println("");
            }
        } else {
            System.out.println("未找到serviceId：" + serviceId + "的实例");
        }

        System.out.println("------------------------------------");
        return list;
    }
	
}

