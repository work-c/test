package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author work
 *
 */

@SpringBootApplication
public class SpringSampleApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringSampleApplication.class, args);
	}



	/**
	 * AWS上でwarファイルを稼働させる為に記載
	 */

	@Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
      return builder.sources(SpringSampleApplication.class);
  }

}
