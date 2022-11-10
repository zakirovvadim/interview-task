package com.mycompany.interviewtask.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
class InterviewTaskApplicationTests {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void checkDatabase() {
        List<Customer> customers = jdbcTemplate.query(
                "SELECT first_name, last_name, phone_number, rating " +
                        "from my_schema.customers",
                (rs, rowNum) -> {
                    Customer customer = new Customer();
                    customer.firstName = rs.getString(1);
                    customer.lastName = rs.getString(2);
                    customer.phoneNumber = rs.getString(3);
                    customer.rating = rs.getInt(4);
                    return customer;
                });
        assertEquals(5, customers.size(), "Must be five customers in db");

        Customer firstCustomer = customers.get(0);
        Customer secondCustomer = customers.get(1);

        assertAll(
                // first
                () -> assertEquals("Ivan", firstCustomer.firstName),
                () -> assertEquals("Ivanov", firstCustomer.lastName),
                () -> assertEquals("+7-999-888-77-66", firstCustomer.phoneNumber),
                () -> assertEquals(101, firstCustomer.rating),
                // second
                () -> assertEquals("John", secondCustomer.firstName),
                () -> assertEquals("Bin", secondCustomer.lastName),
                () -> assertEquals("+4-203-332-27-77", secondCustomer.phoneNumber),
                () -> assertEquals(590, secondCustomer.rating)
        );

    }

}
