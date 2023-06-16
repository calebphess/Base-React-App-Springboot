package com.zetech.thingapp.thingapp.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/*  
 * This is really just here in case we ever want to take our war and deploy it to an external tomcat server.
 * For example if we want to run multiple apps on one server then we can easily just take this war file and put it into a server.
 * If you don't ever plan on doing this and only want to use embedded tomcat you can just delete this file
*/

public class ServletInitializer extends SpringBootServletInitializer 
{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) 
	{
		return application.sources(ThingappApplication.class);
	}

}
