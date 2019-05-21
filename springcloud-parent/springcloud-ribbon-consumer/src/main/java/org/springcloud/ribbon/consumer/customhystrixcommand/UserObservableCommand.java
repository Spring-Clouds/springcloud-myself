/**
 * Project Name:springcloud-ribbon-consumer
 * File Name:HystrixObservableCommand.java
 * Package Name:org.springcloud.ribbon.consumer.customhystrixcommand
 * Date:2019年5月15日下午2:13:12
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.ribbon.consumer.customhystrixcommand;

import org.springcloud.ribbon.consumer.dto.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixObservableCommand;
import com.netflix.hystrix.HystrixThreadPoolKey;

import rx.Observable;
import rx.Subscriber;

/**
 * ClassName:HystrixObservableCommand <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2019年5月15日 下午2:13:12 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class UserObservableCommand extends HystrixObservableCommand<User> {
	private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("CommandKey");
	private RestTemplate restTemplate;
	private Long id;
	
	public UserObservableCommand(RestTemplate restTemplate, Long id) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GETTER_KEY"))
				.andCommandKey(GETTER_KEY)
			 );
		this.restTemplate = restTemplate;
		this.id = id;
	}
	
	@Override
	protected Observable<User> construct() {
		return Observable.create(new Observable.OnSubscribe<User>() {
			@Override
			public void call (Subscriber<? super User> observer) {
				try {
					if (!observer.isUnsubscribed()) {
						User user = restTemplate.getForObject("http://PROVIDER-SERVICE/users/findone/{1}", User.class, id);
						observer.onNext(user);
						observer.onCompleted();
					}
				} catch (Exception e) {
					observer.onError(e);
				}
			}
		});
	}
	
	@Override
	protected Observable<User> resumeWithFallback() {
		return super.resumeWithFallback();
	}
	
	

}

