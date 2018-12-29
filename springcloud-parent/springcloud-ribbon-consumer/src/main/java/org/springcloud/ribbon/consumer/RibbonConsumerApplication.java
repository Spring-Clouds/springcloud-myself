package org.springcloud.ribbon.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 
 */
/**
 * ClassName: EurekaRibbonConsumerApplication <br/>
 * Function: Ribbon是一个基于HTTP和TCP的客户端负载均衡器，它可以在通过客户端中配置的 ribbonServerList服务端列表取轮询访问已达到均衡负载的作用。 <br/>
 *	  @EnableDiscoveryClient
 *	  		激活 Eureka 中的 DiscoverClient 实现。（自动化配置，创建 DiscoveryClient 接口针对 Eureka 客户端的 EurekaDiscoveryClient 实例）
 *	  		才能实现 Controller 中对服务信息的输出。
 *
 * date: 2018年12月26日 上午9:54:06 <br/>
 *
 * @version 
 * @since JDK 1.8
 * @author kaiyun
 */
@EnableDiscoveryClient	// 让应用注册为 Eureka 客户端应用，以获得服务发现的能力。
@SpringBootApplication
public class RibbonConsumerApplication{
	
	@Bean
	@LoadBalanced	// 开启客户端负载均衡
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
    public static void main( String[] args )
    {
    	SpringApplication.run(RibbonConsumerApplication.class, args);
    }
}
