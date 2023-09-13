package com.captain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
//@ComponentScan("com.captain")
@EnableFeignClients

public class LandWebProviderApplication {
	/**
	 * 项目启动类
	 */
	public static void main(String[] args) {
		SpringApplication.run(LandWebProviderApplication.class, args);

	}
}
