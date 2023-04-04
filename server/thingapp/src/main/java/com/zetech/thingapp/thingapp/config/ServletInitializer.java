package com.zetech.thingapp.thingapp.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// TODO: I don't know if this is actually needed so I might delete it later, it was made by default

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ThingappApplication.class);
	}

}
