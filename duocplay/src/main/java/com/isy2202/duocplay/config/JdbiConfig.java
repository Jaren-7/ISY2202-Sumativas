package com.isy2202.duocplay.config;

import javax.sql.DataSource;

import org.jdbi.v3.core.Jdbi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class JdbiConfig {
     
    @Bean
    public Jdbi jdbi(DataSource dataSource){
        return Jdbi.create(dataSource);
    }
    
}
