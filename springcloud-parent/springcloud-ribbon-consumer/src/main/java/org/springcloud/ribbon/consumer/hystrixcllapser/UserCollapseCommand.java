/**
 * Project Name:springcloud-ribbon-consumer
 * File Name:UserCollapseCommand.java
 * Package Name:org.springcloud.ribbon.consumer.hystrixcllapser
 * Date:2019年1月11日下午5:58:42
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.ribbon.consumer.hystrixcllapser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springcloud.ribbon.consumer.customhystrixcommand.UserBatchCommand;
import org.springcloud.ribbon.consumer.dto.User;
import org.springcloud.ribbon.consumer.service.UserService;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;

/**
 * ClassName:UserCollapseCommand <br/>
 * Function: 【服务容错保护Hystrix - 请求合并】请求合并器（通过继承 HystrixCollapser 实现） . <br/>
 * Date:     2019年1月11日 下午5:58:42 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class UserCollapseCommand extends HystrixCollapser<List<User>, User, Long> {
	
	private UserService userService;
	private Long userId;

	
	/**
	 * Creates a new instance of UserCollapseCommand.
	 *
	 * @param userService
	 * @param userId
	 */
	public UserCollapseCommand(UserService userService, Long userId) {
		// 为请求合并器设置了时间延迟属性，合并器会在该时间窗内收集获取单个 User 的请求并在时间窗结束时进行合并组装成单个批量请求。
		super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("userServiceCommand"))
				.andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(200))
				);
		this.userService = userService;
		this.userId = userId;
	}

	/**
	 * TODO 返回单个请求参数userId（可选）.
	 * @see com.netflix.hystrix.HystrixCollapser#getRequestArgument()
	 */
	@Override
	public Long getRequestArgument() {
		return userId;
	}
	
	/**
	 * createCommand 和 mapResponseToRequests 是请求合并器的两个核心。
	 * 
	 * 	createCommand: 该方法的 collapsedRequests 参数中保存了延迟时间窗中收集到的所有获取单个User的请求。
	 * 	通过获取这些请求的参数来组织上面我们准备的批量请求命令 UserBatchCommand 实例。
	 * 
	 * 	mapResponseToRequests: 在批量请求命令 UserBatchCommand 实例被触发执行完成之后，该方法开始执行，其中
	 * 	batchResponse 参数保存了 createCommand 中组织的批量请求命令的返回结果，而 collapsedRequests 参数则
	 * 	代表了每个被合并的请求。在这里我们通过遍历批量结果 batchResponse 对象，为 collapsedRequests 中每个合并
	 * 	前的单个请求设置返回结果，以此完成批量结果到单个请求结果的转换。
	 */

	/**
	 * TODO 
	 * 	createCommand: 该方法的 collapsedRequests 参数中保存了延迟时间窗中收集到的所有获取单个User的请求。
	 * 	通过获取这些请求的参数来组织上面我们准备的批量请求命令 UserBatchCommand 实例。.<br/>
	 * @see com.netflix.hystrix.HystrixCollapser#createCommand(java.util.Collection)
	 */
	@Override
	protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, Long>> collapsedRequests) {
		List<Long> userIds = new ArrayList<>(collapsedRequests.size());
		userIds.addAll(collapsedRequests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));
		return new UserBatchCommand(userService, userIds);
	}

	/**
	 * TODO 
	 *  mapResponseToRequests: 在批量请求命令 UserBatchCommand 实例被触发执行完成之后，该方法开始执行，<br/>
	 * 	其中batchResponse 参数保存了 createCommand 中组织的批量请求命令的返回结果，<br/>
	 * 	而 collapsedRequests 参数则 代表了每个被合并的请求。在这里我们通过遍历批量结果 batchResponse 对象，为 collapsedRequests 中每个合并
	 * 	前的单个请求设置返回结果，以此完成批量结果到单个请求结果的转换。<br/>
	 * @see com.netflix.hystrix.HystrixCollapser#mapResponseToRequests(java.lang.Object, java.util.Collection)
	 */
	@Override
	protected void mapResponseToRequests(List<User> batchResponse, Collection<CollapsedRequest<User, Long>> collapsedRequests) {
		int count = 0;
		for(CollapsedRequest<User, Long>  collapsedRequest : collapsedRequests) {
			User user = batchResponse.get(count++);
			collapsedRequest.setResponse(user);
		}
		
	}

}

