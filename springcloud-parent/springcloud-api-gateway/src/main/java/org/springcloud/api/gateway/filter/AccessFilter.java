/**
 * Project Name:springcloud-api-gateway
 * File Name:AccessFilter.java
 * Package Name:org.springcloud.api.gateway.filter
 * Date:2019年1月30日下午1:21:28
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.api.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName:AccessFilter <br/>
 * Function: Spring Cloud Zuul：API网关服务-Zuul过滤器. <br/>
 * Date:     2019年1月30日 下午1:21:28 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Slf4j
public class AccessFilter extends ZuulFilter {

	/**
	 * 过滤器的类型，他决定过滤器在请求的哪个生命周期中执行。这里定义为 pre，代表会在请求被路由之前执行.
	 * @see com.netflix.zuul.ZuulFilter#filterType()
	 */
	@Override
	public String filterType() {
		return "pre";
	}

	/**
	 * 过滤器的执行顺序。当请求在一个阶段中存在多个过滤器时，需要根据该方法返回的值来依次执行.
	 * @see com.netflix.zuul.ZuulFilter#filterOrder()
	 */
	@Override
	public int filterOrder() {
		return 0;
	}
	
	/**
	 * 判断该过滤器是否需要被执行。这里我们直接返回了 true，因此该过滤器对所有请求都会生效。实际运用中我们可以利用该函数来指定过滤器的有效范围.
	 * @see com.netflix.zuul.IZuulFilter#shouldFilter()
	 */
	@Override
	public boolean shouldFilter() {
		return true;
	}
	
	/**
	 * 过滤器的具体逻辑.
	 * @see com.netflix.zuul.IZuulFilter#run()
	 */
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString() );
		
		Object accessToken = request.getParameter("accessToken");
		if (accessToken==null) {
			log.warn("access token is empty");
			//令Zuul过滤该请求，不对其进行路由
			ctx.setSendZuulResponse(false);
			//设置返回的错误码（当然也可以进一步优化我们的返回，通过ctx.setResponseBody(body)对返回的body内容进行编辑等。
			ctx.setResponseStatusCode(401);
			return null;
		}
		log.info("access token ok");
		return null;
	}

}

