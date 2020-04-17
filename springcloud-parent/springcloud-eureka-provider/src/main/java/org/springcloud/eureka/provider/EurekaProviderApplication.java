package org.springcloud.eureka.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @EnableDiscoveryClient
 * 		激活 Eureka 中的 DiscoverClient 实现。（自动化配置，创建 DiscoveryClient 接口针对 Eureka 客户端的 EurekaDiscoveryClient 实例）
 * 		才能实现 Controller 中对服务信息的输出。
 * 		让应用注册为 Eureka 客户端应用，以获得服务发现的能力
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableEurekaClient
public class EurekaProviderApplication{
    public static void main( String[] args )
    {
    	SpringApplication.run(EurekaProviderApplication.class, args);
    }
}
