/**
 * Project Name:springcloud-ribbon-consumer
 * File Name:UserCommand.java
 * Package Name:org.springcloud.ribbon.consumer.customhystrixcommand
 * Date:2019年1月11日下午3:00:45
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.ribbon.consumer.customhystrixcommand;

import org.springcloud.ribbon.consumer.dto.User;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;

/**
 * ClassName:UserCommand <br/>
 * Function: 【服务容错保护Hystrix - 自定义请求命令】自定义请求命令. <br/>
 * Date:     2019年1月11日 下午3:00:45 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class UserCommand extends HystrixCommand<User> {
	
	private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("CommandKey");
	private RestTemplate restTemplate;
	private Long id;
	
	
	public UserCommand(RestTemplate restTemplate, Long id) {
		/**
		 * 	命令名称、分组以及线程池划分【5.3.3 异常处理】
		 * 	1、通过继承方式实现的 Hystrix 命令使用类名作为默认的命令名称，我们也可以在构造函数中通过 Setter 静态类来设置。
		 * 	2、先调用 withGroupKey 来设置命令组名，然后再通过调用 andCommandKey 来设置命令名（只有 withGroupKey 静态函数可以创建 Setter 的实例）。
		 * 	3、设置命令组的作用
		 * 		根据组来组织和统计命令的告警、仪表盘等信息。
		 * 		根据命令组的划分来分配 Hystrix 的线程池（Hystrix 还提供了 HystrixThreadPoolKey 来对线程池进行更细粒度的线程池划分）。
		 * 	4、在没有特别指定 HystrixThreadPoolKey 的情况下，依然会使用命令组的方式来划分线程池。
		 * 	5、通常情况下，尽量通过 HystrixThreadPoolKey 的方式来指定线程池的划分，而不是通过组名的默认方式实现划分，因为多个命令可能
		 * 	从业务逻辑上来看属于同一个组，但是往往从实现本身上需要跟其他命令进行隔离。
		 */
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GETTER_KEY"))
				.andCommandKey(GETTER_KEY)
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("ThreadPoolKey"))
			 );
		this.restTemplate = restTemplate;
		this.id = id;
	}
	
	/**
	 * TODO 服务降级的方法（是Hystrix命令执行失败时使用的后备方法，用来实现服务的降级处理逻辑）（可选）.
	 * @see com.netflix.hystrix.HystrixCommand#getFallback()
	 */
	@Override
    protected User getFallback() {
		//异常传播与捕获。若是通过继承实现，则通过 Throwable getExecutionException() 方法获取具体的异常，通过判断来进入不同的处理逻辑
		//getExecutionException().getStackTrace();	
		User user = new User();
		user.setId(null);
		user.setUserName("调用出异常啦，启用熔断器");
        return user;
    }

	@Override
	protected User run() throws Exception {
		User user = restTemplate.getForObject("http://PROVIDER-SERVICE/users/findone/{1}", User.class, id);
		
//		// 若上面有写的操作，则需要刷新缓存，清理缓存中失效的User
//		this.flushCache(id);
		
		return user;
	}
	
	/**
	 * TODO 【Hystrix - 开启请求缓存功能（见5.3.3 异常处理-请求缓存）】<br/>
	 * 	1、只需要在实现 HystrixCommand 或 HystrixObservableCommand时，通过重载 getCacheKey() 方法来开启请求缓存。<br/>
	 *	2、当不同的外部请求处理逻辑调用了同一个依赖服务时，Hystrix 会根据 getCacheKey 方法返回的值来区分是否
	 *	是重复的请求，如果它们的 cacheKey 相同，那么该依赖服务只会在第一个请求到达时被真实地调用一次，
	 *	另外一个请求则是直接从请求缓存中返回结果。<br/>
	 *	3、若只是读操作，则不需要考虑缓存内容是否正确的问题。若有更新数据的写操作，则在进行写操作时进行及时处理，以防止读操作的请求命令获取到了失效的数据（见如下flushCache方法）。<br/>
	 * @see com.netflix.hystrix.AbstractCommand#getCacheKey()
	 */
//	@Override
//	protected String getCacheKey() {
//		return String.valueOf(id);
//	}
	
	/**
	 * flushCache: 【Hystrix - 清理失效缓存功能】. <br/>
	 * 	1、HystrixRequestCache.clear() 方法来进行缓存的清理
	 *
	 * @param id
	 * @since JDK 1.8
	 * @author kaiyun
	 */
	public void flushCache(Long id) {
		HystrixRequestCache.getInstance(GETTER_KEY, HystrixConcurrencyStrategyDefault.getInstance())
				.clear(String.valueOf(id));
	}
}

