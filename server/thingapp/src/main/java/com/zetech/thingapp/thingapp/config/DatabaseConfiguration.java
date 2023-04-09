package com.zetech.thingapp.thingapp.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

/*
 * Sets up Mybatis, and creates a session factory for the application.
 * This primarily enables all our database connections and threading automagically
*/

@Configuration
// MapperScan tells mybatis to look in java/MapperScan location to find Interface Java files 
//  and resource/MapperScan to find XML files with matching names
@MapperScan("com.zetech.thingapp.thingapp.dal.interfaces")
public class DatabaseConfiguration {
  @Bean
  @ConfigurationProperties(prefix = "app.datasource")
  public HikariDataSource dataSource()
  {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }

  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception
  {
    SqlSessionFactoryBean sfb = new SqlSessionFactoryBean();
    sfb.setDataSource(dataSource());
    SqlSessionFactory sf = sfb.getObject();
    sf.getConfiguration().setMapUnderscoreToCamelCase(true);
    return sf;
  }
}
