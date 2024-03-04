package com.mycompany.interviewtask.impl;

import com.mycompany.interviewtask.repository.CustomerRepositoryImpl;
import com.mycompany.interviewtask.repository.RatingRepositoryImpl;
import com.mycompany.interviewtask.service.FileServiceImpl;
import com.mycompany.interviewtask.service.CustomerServiceImpl;
import com.mycompany.interviewtask.service.interfaces.ParserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

/**
 * Описание тестов в классе IntegrationTestExample
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class InterviewTaskApplicationTests {

    public static final String QUERY = "SELECT first_name, last_name, phone_number, rating " +
            "from my_schema.customers";

    @Mock
    JdbcTemplate jdbcTemplate;
    @InjectMocks
    RatingRepositoryImpl ratingRepository;
    @Mock
    CustomerRepositoryImpl customerRepository;
    @Mock
    FileServiceImpl fileService;
    @Mock
    ParserService parserService;

    @Test
    void checkDatabase() throws IOException {
        CustomerServiceImpl saveCustomers =
                new CustomerServiceImpl(ratingRepository, customerRepository,
                        fileService, parserService);

        when(parserService.deserialise(any(), any())).thenReturn(Collections.emptyList());
        when(ratingRepository.findAllRatings()).thenReturn(Collections.emptyList());
        when(customerRepository.updateCustomers(anyList())).thenReturn(new int[]{1});

        saveCustomers.saveCustomers();
        verify(fileService, atLeastOnce()).saveNumbersToFile(anyList());
        verify(customerRepository, atLeastOnce()).updateCustomers(anyList());
        verify(parserService, atLeastOnce()).deserialise(any(), any());

//        Customer firstCustomer = customers.get(0);
//        Customer secondCustomer = customers.get(1);
//
//        assertAll(
//                // first
//                () -> assertEquals("Ivan", firstCustomer.getFirstName()),
//                () -> assertEquals("Ivanov", firstCustomer.getLastName()),
//                () -> assertEquals("+7-999-888-77-66", firstCustomer.getPhoneNumber()),
//                () -> assertEquals(101, firstCustomer.getRating()),
//                // second
//                () -> assertEquals("John", secondCustomer.getFirstName()),
//                () -> assertEquals("Bin", secondCustomer.getLastName()),
//                () -> assertEquals("+4-203-332-27-77", secondCustomer.getPhoneNumber()),
//                () -> assertEquals(590, secondCustomer.getRating())
//        );

    }

}
