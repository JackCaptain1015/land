package com.captain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
//@ComponentScan("com.captain")
@EnableFeignClients
public class LandWebApplication {
	/**
	 * 项目启动类
	 */
	public static void main(String[] args) {
		SpringApplication.run(LandWebApplication.class, args);

	}
}
