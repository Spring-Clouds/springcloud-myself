/**
 * Project Name:springcloud-ribbon-consumer
 * File Name:RibbonConsumerController.java
 * Package Name:org.springcloud.ribbon.consumer.controller
 * Date:2018年12月26日上午9:56:19
 * Copyright (c) 2018, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.ribbon.consumer.controller;

import java.util.List;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springcloud.ribbon.consumer.customhystrixcommand.UserBatchCommand;
import org.springcloud.ribbon.consumer.dto.User;
import org.springcloud.ribbon.consumer.hystrixcllapser.UserCollapseCommand;
import org.springcloud.ribbon.consumer.service.HelloService;
import org.springcloud.ribbon.consumer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName:RibbonConsumerController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年12月26日 上午9:56:19 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Slf4j
@RestController
public class RibbonConsumerController {
	
	@Autowired
	private DiscoveryClient client;
	
//	@Autowired
//	private RestTemplate restTemplate;
	
	@Autowired
	private HelloService helloService;
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value="/ribbon-consumer", method=RequestMethod.GET)
	public String helloConsumer() {
//		return restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class).getBody();
		return helloService.hello();
	}
	
	@RequestMapping(value="/ribbon-consumer/users/sync/{id}", method=RequestMethod.GET)
	public String getUserByIdSyncUnAnnotations(@PathVariable("id") Long id) throws Exception {
		User user = userService.getUserByIdSyncUnAnnotations(id);
		
		ServiceInstance instance = client.getLocalServiceInstance();
		log.info("/ribbon-consumer/users/sync/{}, host:{}, service_id:{}", id, instance.getHost(), instance.getServiceId());
		
		return user.toString();
	}
	
	@RequestMapping(value="/ribbon-consumer/users/async/{id}", method=RequestMethod.GET)
	public String getUserByIdAsyncUnAnnotations(@PathVariable("id") Long id) throws Exception {
		User user = userService.getUserByIdAsyncUnAnnotations(id).get();
		
		ServiceInstance instance = client.getLocalServiceInstance();
		log.info("/ribbon-consumer/users/async/{}, host:{}, service_id:{}", id, instance.getHost(), instance.getServiceId());
		
		return user.toString();
	}
	
	@RequestMapping(value="/ribbon-consumer/users/sync/anno/{id}", method=RequestMethod.GET)
	public String getUserByIdSyncAnnotations(@PathVariable("id") Long id) throws Exception {
		User user = userService.getUserByIdSyncAnnotations(id);
		
		ServiceInstance instance = client.getLocalServiceInstance();
		log.info("/ribbon-consumer/users/sync/anno/{}, host:{}, service_id:{}", id, instance.getHost(), instance.getServiceId());
		
		return user.toString();
	}
	
	@RequestMapping(value="/ribbon-consumer/users/async/anno/{id}", method=RequestMethod.GET)
	public String getUserByIdAsyncAnnotations(@PathVariable("id") Long id) throws Exception {
		User user = userService.getUserByIdAsyncAnnotations(id).get();
		
		ServiceInstance instance = client.getLocalServiceInstance();
		log.info("/ribbon-consumer/users/async/anno/{}, host:{}, service_id:{}", id, instance.getHost(), instance.getServiceId());
		
		return user.toString();
	}
	
	/**
	 * findOne:【服务容错保护Hystrix - 请求合并】继承HystrixCollapser类实现（见UserCollapseCommand类）. <br/>
	 *
	 * @throws Exception
	 * @since JDK 1.8
	 * @author kaiyun
	 */
	@RequestMapping(value="/ribbon-consumer/users/findone/hystrix/collapse", method=RequestMethod.GET)
	public void findOne(HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		HystrixRequestContext context = HystrixRequestContext.initializeContext();
		UserCollapseCommand command = new UserCollapseCommand(userService,1L);
		UserCollapseCommand command1 = new UserCollapseCommand(userService,2L);
		UserCollapseCommand command2 = new UserCollapseCommand(userService,3L);

        //这里你必须要异步，因为同步是一个请求完成后，另外的请求才能继续执行，所以必须要异步才能请求合并
        Future<User> future = command.queue();
        Future<User> future1 = command1.queue();

        User r = future.get();
        User r1 = future1.get();

        Thread.sleep(2000);
        
        //可以看到前面两条命令会合并，最后一条会单独，因为睡了2000毫秒，而你请求设置要求在200毫秒内才合并的。
        Future<User> future2 = command2.queue();
        User r2 = future2.get();

        System.out.println(r.toString());
        System.out.println(r1.toString());
        System.out.println(r2.toString());

        context.close();
        
        ServiceInstance instance = client.getLocalServiceInstance();
        log.info("/ribbon-consumer/users/find, host:{}, service_id:{}", instance.getHost(), instance.getServiceId());
	}
	
	/**
	 * findOneByAnnotation:【服务容错保护Hystrix - 请求合并】注解实现（@HystrixCollapser）. <br/>
	 *
	 * @throws Exception
	 * @since JDK 1.8
	 * @author kaiyun
	 */
	@RequestMapping(value="/ribbon-consumer/users/findone/hystrix/collapse/annotation", method=RequestMethod.GET)
	public void findOneByAnnotation() throws Exception {
		HystrixRequestContext context = HystrixRequestContext.initializeContext();
	    Future<User> f1 = userService.findOne(1l);
	    Future<User> f2 = userService.findOne(2l);
	    Future<User> f3 = userService.findOne(3l);
	    User b1 = f1.get();
	    User b2 = f2.get();
	    User b3 = f3.get();
	    
	    Thread.sleep(2000);
	    
	    Future<User> f4 = userService.findOne(4l);
	    User b4 = f4.get();
	    System.out.println("b1>>>"+b1);
	    System.out.println("b2>>>"+b2);
	    System.out.println("b3>>>"+b3);
	    System.out.println("b4>>>"+b4);
	    
	    context.close();
	    
	    ServiceInstance instance = client.getLocalServiceInstance();
	    log.info("/ribbon-consumer/users/find, host:{}, service_id:{}", instance.getHost(), instance.getServiceId());
	}
	
}

