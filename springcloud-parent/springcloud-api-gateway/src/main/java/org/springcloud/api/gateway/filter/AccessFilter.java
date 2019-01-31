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

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName:AccessFilter <br/>
 * Function: Spring Cloud Zuul：API网关服务-Zuul过滤器. <br/>
 * 		在实现自定义过滤器之后，它并不会直接生效，还需要为其创建具体的 Bean 才能启动该过滤器. <br/>
 * Date:     2019年1月30日 下午1:21:28 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Slf4j
//@Component	//要么在启动类中@Bean声明，要么用@component在这里声明
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
		//执行顺序  0 靠前执行 在spring cloud zuul提供的pre过滤器之后执行，默认的是小于0的。    
        //除了参数校验类的过滤器 一般上直接放在 PreDecoration前
        //即：PRE_DECORATION_FILTER_ORDER - 1;
        //常量类都在：org.springframework.cloud.netflix.zuul.filters.support.FilterConstants 下
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
		//获取请求的上下文类 注意是：com.netflix.zuul.context包下的
		RequestContext ctx = RequestContext.getCurrentContext();
		//获取request对象
		HttpServletRequest request = ctx.getRequest();
		//避免中文乱码
        ctx.addZuulResponseHeader("Content-type", "text/json;charset=UTF-8");
        ctx.getResponse().setCharacterEncoding("UTF-8");
        //打印日志
        log.info("请求方式：{},地址：{}", request.getMethod(),request.getRequestURI().toString());
		
		Object accessToken = request.getParameter("accessToken");
		if (accessToken==null) {
			log.warn("access token is empty");
			//令Zuul过滤该请求，不对其进行路由
			ctx.setSendZuulResponse(false);
			//设置返回的错误码（当然也可以进一步优化我们的返回，通过ctx.setResponseBody(body)对返回的body内容进行编辑等。
			ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());//401
			ctx.setResponseBody("{\"code\":\"9999\",\"msg\":\"非法访问\"}");
			//或者添加一个额外参数也可以 传递参数可以使用
//          ctx.set("checkAuth",false);
			return null;
		}
		log.info("access token ok");
		return null;
	}

}

