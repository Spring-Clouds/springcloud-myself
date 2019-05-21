/**
 * Project Name:springcloud-ribbon-consumer
 * File Name:HelloService.java
 * Package Name:org.springcloud.ribbon.consumer.service
 * Date:2019年1月9日下午6:55:33
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.ribbon.consumer.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.springcloud.ribbon.consumer.customhystrixcommand.UserObservableCommand;
import org.springcloud.ribbon.consumer.customhystrixcommand.UserCommand;
import org.springcloud.ribbon.consumer.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand.Setter;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Subscriber;

/**
 * ClassName:UserService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2019年1月9日 下午6:55:33 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Slf4j
@Service
public class UserService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	/**
	 * syncUnAnnotationsgetUserById: 使用自定义请求命令，实现请求的同步执行. <br/>
	 *
	 * @param id
	 * @return
	 * @since JDK 1.8
	 * @author kaiyun
	 */
	public User getUserByIdSyncUnAnnotations(Long id) {
		long start = System.currentTimeMillis();
		UserCommand userCommand = new UserCommand(restTemplate, id);
		User user = userCommand.execute();	// 同步执行命令
		long end = System.currentTimeMillis();
		log.info("----------Spend time:{}", (end-start));
		return user;
	}
	
	/**
	 * asyncUnAnnotationsgetUserById: 使用自定请求命令，实现请求的异步执行. <br/>
	 *
	 * @param id
	 * @return
	 * @since JDK 1.8
	 * @author kaiyun
	 * @throws Exception 
	 */
	public User getUserByIdAsyncUnAnnotations(Long id) throws Exception {
		long start = System.currentTimeMillis();
		UserCommand userCommand = new UserCommand(restTemplate, 1L);
		Future<User> futrueUser = userCommand.queue();	// 异步执行命令
		User user = futrueUser.get();
		long end = System.currentTimeMillis();
		log.info("----------Spend time:{}", (end-start));
		return user;
	}
	
	// @HystrixComand 注解是Hystrix中的核心注解，通过它创建了 HystrixComand 的实现，同时利用 fallback 属性指定了服务降级的实现方法。
	// @HystrixComand 注解实现请求的同步执行，若要实现异步执行则需要另外定义。
	// 通过继承的方式来实现，实现 HystrixCommand 类。即可实现请求的同步执行也可以实现异步执行。（详细见5.3.1）
	
	/**
	 * syncAnnotationsGetUserById: 【传统方式】使用注解@HystrixComand，实现请求的“同步”执行. <br/>
	 *
	 * @param id
	 * @return
	 * @since JDK 1.8
	 * @author kaiyun
	 */
	@HystrixCommand(fallbackMethod = "getUserByIdFallback")
	public User getUserByIdSyncAnnotations(Long id) {
		long start = System.currentTimeMillis();
		User user = restTemplate.getForObject("http://PROVIDER-SERVICE/users/findone/{1}", User.class, id);
		long end = System.currentTimeMillis();
		log.info("----------Spend time:{}", (end-start));
		
		return user; 
	}
	
	/**
	 * asyncAnnotationsGetUserByIdAsync: 【传统方式】使用注解@HystrixComand，实现请求的“异步”处理. <br/>
	 *
	 * @param id
	 * @return
	 * @since JDK 1.8
	 * @author kaiyun
	 */
	@HystrixCommand(fallbackMethod = "getUserByIdFallback")
	public User getUserByIdAsyncAnnotations(final Long id) throws Exception {
		long start = System.currentTimeMillis();
		Future<User> futrueUser = new AsyncResult<User>() {
			@Override
			public User invoke() {
				return restTemplate.getForObject("http://PROVIDER-SERVICE/users/findone/{1}", User.class, id);
			}
		};
		long end = System.currentTimeMillis();
		log.info("----------Spend time:{}", (end-start));
		return futrueUser.get();
	}
	
	// 除了传统的同步执行与异步执行之外，还可以将 HystrixCommand 通过 Observable 来实现响应式执行方式。
	// 通过调用 observe() 和 toObservable() 方法可以返回 Observable 对象
	
	/**
	 * getUserByIdForObserve:【响应式执行方式】通过注解@HystrixCommand，获取能发射一次的Obserable对象. <br/>
	 * &nbsp;observe() 和 toObservable()虽然都返回了 Observable，但是他们略有不同：.<br/>
	 * &nbsp;前者返回的是一个 Hot Observable，该命令会在 observe() 调用的时候立即执行,当Observable每次被订阅时会重访它的行为. <br/>
	 * &nbsp;后者返回的是一个 Cold Observable，toObservable()执行之后，命令不会被立即执行，只有当所有订阅者都订阅它之后才会执行. <br/>
	 *
	 * 局限性：
	 * 返回的 Observable 只能发射一次数据
	 * @param id
	 * @return
	 * @since JDK 1.8
	 * @author kaiyun
	 */
	@HystrixCommand(fallbackMethod = "getUserByIdFallback")
	public User getUserByIdForObservableAnnotationsOne(final Long id) throws Exception {
		long start = System.currentTimeMillis();
		UserCommand userCommand = new UserCommand(restTemplate, id);
		Observable<User> observe = null;
		if (id>1L) {
			observe = userCommand.toObservable();
		} else {
			observe = userCommand.observe();
		}
		long end = System.currentTimeMillis();
		log.info("----------Spend time:{}", (end-start));
		return observe.toBlocking().toFuture().get();
	}
	
	//虽然 HystrixCommand 具备了 Observe() 和 toObservable() 的功能，但是它的实现有一定的局限性，它返回的 Observable 只能发射一次数据。
	//所以Hystrix还提供了另外一个特殊命令封装 HystrixObservableCommand，通过它实现的命令可以获取能发射多次的 Observable。
	
	/**
	 * getUserByIdForObservableUnAnnotations:【响应式执行方式】通过命令封装 HystrixObservableCommand对象，获取能发射多次的Obserable对象. <br/>
	 *
	 * @param id
	 * @return
	 * @since JDK 1.8
	 * @author kaiyun
	 */
	public User getUserByIdForObservableUnAnnotationsMany(final Long id) throws Exception {
		long start = System.currentTimeMillis();
		Observable<User> observe = null;
		UserObservableCommand hystrixObservableCommand = new UserObservableCommand(restTemplate, 1L);
		if (id>1L) {
			observe = hystrixObservableCommand.toObservable();
		} else {
			observe = hystrixObservableCommand.observe();
		}
		long end = System.currentTimeMillis();
		log.info("----------Spend time:{}", (end-start));
		
		return observe.toBlocking().toFuture().get();
	}
	
	/**
	 * getUserByIdForObservableAnnotations:【响应式执行方式】通过注解@HystrixCommand，获取能发射多次的Obserable对象. <br/>
	 *
	 * @param id
	 * @return
	 * @since JDK 1.8
	 * @author kaiyun
	 */
	@HystrixCommand(observableExecutionMode=ObservableExecutionMode.EAGER)//EAGER表示使用observe()执行方式，LAZY表示使用toObservable()执行方式
	public User getUserByIdForObservableAnnotationsMany(final Long id) throws Exception {
		long start = System.currentTimeMillis();
		Observable<User> observe = Observable.create(new Observable.OnSubscribe<User>() {
			@Override
			public void call(Subscriber<? super User> observer) {
				try {
					if (!observer.isUnsubscribed() ) {
						User user = restTemplate.getForObject("http://PROVIDER-SERVICE/users/findone/{1}", User.class, id);
						observer.onNext(user);
						observer.onCompleted();
					}
				} catch (Exception e) {
					observer.onError(e);
				}
			}
		});
		long end = System.currentTimeMillis();
		log.info("----------Spend time:{}", (end-start));
		
		return observe.toBlocking().toFuture().get();
	}
	
	/**
	 * getUserByIdFallback: Hystrix - 服务降级的方法（是Hystrix命令执行失败时使用的后备方法，用来实现服务的降级处理逻辑）. <br/>
	 *
	 * @param id Long
	 * @param e	Throwable（Hystrix 异常传播）
	 * @return
	 * @since JDK 1.8
	 * @author kaiyun
	 */
	private User getUserByIdFallback(Long id, Throwable e) {
		User user = new User();
		user.setId(id);
		user.setUserName("【findone】调用出异常啦，启用熔断器");
		
		e.getStackTrace();
		
		return user;
	}
	
// =================================== 服务容错保护 - 请求合并 - 方法 - begin =========================================
	
	// @HystrixCollapser 实现了在 /users/{id} 依赖服务之前设置了一个批量请求合并器
	@HystrixCollapser(batchMethod="findAll", collapserProperties={@HystrixProperty(name="timerDelayInMilliseconds", value="200")})
	public Future<User> findOne(Long userId) {
		return null; 
	}
	
	/**
	 * getAllUser: 获取所有的用户. <br/>
	 *
	 * @param id
	 * @return
	 * @since JDK 1.8
	 * @author kaiyun
	 */
	@HystrixCommand(fallbackMethod = "getUsersFallback")
	public List<User> findAll(List<Long> userIds) {
		long start = System.currentTimeMillis();
		
//		参考链接：RestTemplate.getForObject将PO中List的泛型变成LinkedHashMap问题的解决： https://blog.csdn.net/amosryan/article/details/54019479
//		List<User> userList = restTemplate.getForObject("http://PROVIDER-SERVICE/users/findall/{1}", new ArrayList<User>().getClass(), StringUtils.join(userIds, ","));
		
		List<User> userList = Collections.emptyList();
		try {
			ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<List<User>>() { };
			HttpHeaders headers = new HttpHeaders();
			headers.set("userIds", StringUtils.join(userIds, ","));
			HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(null,headers);
			ResponseEntity<List<User>> responseEntity = restTemplate.exchange(
					"http://provider-service/users/findall",
					HttpMethod.GET, httpEntity, typeRef);
			userList = responseEntity.getBody();
		} catch (Exception e) {
			log.error("-------------findall 失败！", e);;
			throw e;
		}
		
		long end = System.currentTimeMillis();
		log.info("----------Spend time:{}", (end-start));
		
		return userList; 
	}
	
	/**
	 * getUsersFallback: Hystrix - 服务降级的方法（是Hystrix命令执行失败时使用的后备方法，用来实现服务的降级处理逻辑）. <br/>
	 *
	 * @param userIds	List<Long>
	 * @param e	Throwable（Hystrix 异常传播）
	 * @return
	 * @since JDK 1.8
	 * @author kaiyun
	 */
	public List<User> getUsersFallback(List<Long> userIds, Throwable e) {
		List<User> userList = new ArrayList<>();
		for(Long id : userIds) {
			User user = new User();
			user.setId(id);
			user.setUserName("【findall】调用出异常啦，启用熔断器");
			userList.add(user);
		}
		
		e.getStackTrace();
		
		return userList;
	}
	
// =================================== 服务容错保护 - 请求合并 - 方法 - end =========================================
	
}

