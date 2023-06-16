package com.zetech.thingapp.thingapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

/*  
 * This handles basic configuration for our web server.
 * In this case we are using tomcat as our web server.
 * We are also setting up a redirect from http to https
*/

@Configuration
public class ServerConfiguration 
{

  @Bean
  public ServletWebServerFactory servletContainer() 
  {
    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() 
    {
      @Override
      protected void postProcessContext(Context context) 
      {
        // setting the security constraint to CONFIDENTIAL prevents users from accessing the site over http
        var securityConstraint = new SecurityConstraint();
        securityConstraint.setUserConstraint("CONFIDENTIAL");

        var collection = new SecurityCollection();
        collection.addPattern("/*");

        securityConstraint.addCollection(collection);

        context.addConstraint(securityConstraint);
      }
    };
    tomcat.addAdditionalTomcatConnectors(getHttpConnector());
    return tomcat;
  }

  // This will redirect http traffic on 8080 to https on 8443
  private Connector getHttpConnector() 
  {
    var connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);

    connector.setScheme("http");
    connector.setPort(8080);
    connector.setSecure(false);
    connector.setRedirectPort(8443);

    return connector;
  }

}

