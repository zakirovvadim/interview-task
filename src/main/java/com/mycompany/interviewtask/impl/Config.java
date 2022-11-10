package com.mycompany.interviewtask.impl;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

@Profile("!test")
@Configuration
@SuppressWarnings("all")
public class Config {

    @Value("${db.url}")
    String url;

    @Value("${db.user}")
    String user;

    @Value("${db.password}")
    String password;

    @Bean
    public JdbcTemplate jdbcTemplate() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return new JdbcTemplate(dataSource);
    }

}
