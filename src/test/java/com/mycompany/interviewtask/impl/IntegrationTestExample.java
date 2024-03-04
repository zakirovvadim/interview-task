package com.mycompany.interviewtask.impl;

import com.mycompany.interviewtask.repository.CustomerRepositoryImpl;
import com.mycompany.interviewtask.repository.RatingRepositoryImpl;
import com.mycompany.interviewtask.service.FileServiceImpl;
import com.mycompany.interviewtask.service.NumberServiceImpl;
import com.mycompany.interviewtask.service.ParserServiceImpl;
import com.mycompany.interviewtask.service.CustomerServiceImpl;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Посмотрев на имеющийся тест я решил его сделать с подключением к базе через тестконтейнерс. Потому что имеющийся тест
 * судя по всему предполагал проверку сохранения элементов, путем вызова execute у темплейта. Считаю что конкретно у темплейта
 * вызывать мтеод операции для тестирвоания не требуется, а следует тестировать весь метод, а именно сохранение.
 * Если в этом классе, сохранение и првоерка количество сохраненных потребителей проверяется после полноценного запуска спринга и
 * подключения к базе в контейнере, в другом тестовом классе InterviewTaskApplicationTests, я реализовал юнит тест лишь путем верификации
 * вызовов мтеодов, ответственных за сохранение.
 *
 * Тест в этом классе запускается, но у меня возникли проблемы с непосредственным сохранением, решил оставить и описать лишь общее
 * направление мысли, без углубления к выявленным ошибкам.
 */
@DirtiesContext
@Testcontainers
@SpringBootTest
public class IntegrationTestExample {

    public static final String QUERY = "SELECT first_name, last_name, phone_number, rating " +
            "from my_schema.customers";

    private JdbcTemplate jdbcTemplate;
    @Autowired
    RatingRepositoryImpl ratingRepository;
    @Autowired
    CustomerRepositoryImpl customerRepository;
    @Autowired
    FileServiceImpl fileService;
    @Autowired
    ParserServiceImpl parserService;
    @Autowired
    NumberServiceImpl numberService;

    @Container
    private static PostgreSQLContainer container = new PostgreSQLContainer("postgres:14")
            .withPassword("password")
            .withDatabaseName("postgres")
            .withUsername("postgres");

    @DynamicPropertySource
    public static void configureRegistry(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.db-postgres.url", container::getJdbcUrl);
        registry.add("spring.datasource.db-postgres.username", container::getUsername);
        registry.add("spring.datasource.db-postgres.password", container::getPassword);
        registry.add("spring.datasource.db-postgres.driver-class-name", () -> "org.postgresql.Driver");
        registry.add("spring.jpa.generate-ddl", () -> true);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
    }

    @BeforeEach
    public void setTemplate(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(container.getJdbcUrl());
        dataSource.setUsername(container.getUsername());
        dataSource.setPassword(container.getPassword());
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("CREATE TABLE Rating (id integer primary key , name varchar(100), value integer)");
        jdbcTemplate.execute("CREATE TABLE Customer (id integer primary key , first_name varchar(100), last_name integer, number_of_purchase integer," +
                " number_of_returns integer, phone_number varchar(100), rating integer, status varchar(100))");
        jdbcTemplate.update("insert into rating (id, name, value) values (?, ?, ?)", 1, "platinum", 1000);
        jdbcTemplate.update("insert into rating (id, name, value) values (?, ?, ?)", 2, "gold", 100);
        jdbcTemplate.update("insert into rating (id, name, value) values (?, ?, ?)", 3, "silver", 10);
    }

    @Test
    public void saveCustomerIfSuccess() {
        RatingRepositoryImpl ratingRepository1 = new RatingRepositoryImpl(jdbcTemplate);
        CustomerServiceImpl saveCustomers = new CustomerServiceImpl(ratingRepository1, customerRepository, parserService, numberService);

        saveCustomers.saveCustomers();
        List<com.mycompany.interviewtask.impl.Customer> all =
                jdbcTemplate.query("SELECT * FROM Customer", BeanPropertyRowMapper.newInstance(com.mycompany.interviewtask.impl.Customer.class));
        assertThat(all.size()).isEqualTo(5);
        System.out.println();
    }
}
