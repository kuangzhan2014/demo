package com.maitianer.demo.api;

import com.maitianer.demo.biz.DemoBizConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {DemoBizConfig.class, DemoApiApplication.class})
public class DemoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApiApplication.class, args);
	}

}
