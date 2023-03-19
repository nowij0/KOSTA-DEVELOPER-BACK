package com.developer;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//스프링에서 사용하던 WebInitializer와 같은 역할 
//SpringBootServletInitializer를 상속받아왔다는 것이 중요 ***
public class ServletInitializer extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DeveloperApplication.class);
	}
}