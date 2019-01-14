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
		log.info("/ribbon-consumer/users/sync/{}, host:" + instance.getHost() + ", service_id:{}", id, instance.getServiceId());
		
		return user.toString();
	}
	
	@RequestMapping(value="/ribbon-consumer/users/async/{id}", method=RequestMethod.GET)
	public String getUserByIdAsyncUnAnnotations(@PathVariable("id") Long id) throws Exception {
		User user = userService.getUserByIdAsyncUnAnnotations(id).get();
		
		ServiceInstance instance = client.getLocalServiceInstance();
		log.info("/ribbon-consumer/users/async/{}, host:" + instance.getHost() + ", service_id:{}", id, instance.getServiceId());
		
		return user.toString();
	}
	
	@RequestMapping(value="/ribbon-consumer/users/sync/anno/{id}", method=RequestMethod.GET)
	public String getUserByIdSyncAnnotations(@PathVariable("id") Long id) throws Exception {
		User user = userService.getUserByIdSyncAnnotations(id);
		
		ServiceInstance instance = client.getLocalServiceInstance();
		log.info("/ribbon-consumer/users/sync/anno/{}, host:" + instance.getHost() + ", service_id:{}", id, instance.getServiceId());
		
		return user.toString();
	}
	
	@RequestMapping(value="/ribbon-consumer/users/async/anno/{id}", method=RequestMethod.GET)
	public String getUserByIdAsyncAnnotations(@PathVariable("id") Long id) throws Exception {
		User user = userService.getUserByIdAsyncAnnotations(id).get();
		
		ServiceInstance instance = client.getLocalServiceInstance();
		log.info("/ribbon-consumer/users/async/anno/{}, host:" + instance.getHost() + ", service_id:{}", id, instance.getServiceId());
		
		return user.toString();
	}
	
	
	
	// =================================== 以下是 服务容错保护中，请求合并，方法，通过继承类实现方式，见UserCollapseCommand类=========================================	

	@RequestMapping(value="/ribbon-consumer/users/find", method=RequestMethod.GET)
	public String find() throws Exception {
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

        return null;
	}
	
	@RequestMapping(value="/ribbon-consumer/users/findone/{userId}", method=RequestMethod.GET)
	public String find(@PathVariable("userId") Long userId) throws Exception {
		User user = userService.findOne(userId);
		
		ServiceInstance instance = client.getLocalServiceInstance();
		log.info("/ribbon-consumer/users/find/{}, host:" + instance.getHost() + ", service_id:{}", userId, instance.getServiceId());
		
		return user.toString();
	}
	
	@RequestMapping(value="/ribbon-consumer/users/findall/{userIds}", method=RequestMethod.GET)
	public String find(@PathVariable("userIds") List<Long> userIds) throws Exception {
		List<User> userList = userService.findAll(userIds);
		
		ServiceInstance instance = client.getLocalServiceInstance();
		log.info("/ribbon-consumer/users/findall/{}, host:" + instance.getHost() + ", service_id:{}", StringUtils.join(userIds, ","), instance.getServiceId());
		
		return userList.toString();
	}
	
}

