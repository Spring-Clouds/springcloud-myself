/**
 * Project Name:springcloud-api-gateway
 * File Name:ErrorFilter.java
 * Package Name:org.springcloud.api.gateway.filter
 * Date:2019年1月31日上午11:05:09
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.api.gateway.filter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName:ErrorFilter <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2019年1月31日 上午11:05:09 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Slf4j
//@Component
public class ErrorFilter extends ZuulFilter {
	
	@Override
	public String filterType() {
		return "error";
	}
	
	@Override
	public int filterOrder() {
		return 10;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		Throwable throwable = ctx.getThrowable();
		log.error("this is a ErrorFilter:{}", throwable.getCause().getMessage());
		ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		ctx.set("error.exception", throwable.getCause());
		return null;
	}

}

