package com.captain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.captain")
public class LandBizApplication {
	/**
	 * 项目启动类
	 */
	public static void main(String[] args) {
		SpringApplication.run(LandDalApplication.class, args);

	}
}
