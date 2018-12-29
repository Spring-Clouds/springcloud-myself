package org.springcloud.eureka.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer //启动一个服务注册中心提供给其他应用进行对话
public class EurekaServerApplication{
    public static void main( String[] args )
    {
    	new SpringApplicationBuilder(EurekaServerApplication.class).web(true).run(args);
    }
}
