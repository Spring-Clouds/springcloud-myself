package org.springcloud.eureka.provider2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class EurekaProviderApplication2 
{
    public static void main( String[] args )
    {
        SpringApplication.run(EurekaProviderApplication2.class, args);
    }
}
