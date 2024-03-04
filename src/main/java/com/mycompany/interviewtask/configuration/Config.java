package com.mycompany.interviewtask.configuration; /**
 * Решил делать конфигурацию через проперти файл, но можно и так.
 */

//package com.mycompany.interviewtask.configuration;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import javax.sql.DataSource;
//
//@Profile("!test")
//@Configuration
//public class Config {
//
//    @Value("${db.url}")
//    String url;
//
//    @Value("${db.user}")
//    String user;
//
//    @Value("${db.password}")
//    String password;
//
//    @Bean
//    public DataSource dataSource() {
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl(url);
//        dataSource.setUsername(user);
//        dataSource.setPassword(password);
//        return dataSource;
//    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//}