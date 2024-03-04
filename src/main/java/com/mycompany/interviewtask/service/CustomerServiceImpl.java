package com.mycompany.interviewtask.service;


import com.mycompany.interviewtask.entities.Rating;
import com.mycompany.interviewtask.impl.Customer;
import com.mycompany.interviewtask.repository.CustomerRepositoryImpl;
import com.mycompany.interviewtask.repository.RatingRepositoryImpl;
import com.mycompany.interviewtask.service.interfaces.NumberService;
import com.mycompany.interviewtask.service.interfaces.ParserService;
import com.mycompany.interviewtask.service.interfaces.SaveCustomers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Разделил сохранение потребителей и номеров. Сделал универсальный парсинг, чтобы не зависеть от типов.
 * Сократил неокторые операции до стримов.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements SaveCustomers {

    public static final String PATHNAME = "src/main/resources/customers.json";
    private final RatingRepositoryImpl ratingRepository;
    private final CustomerRepositoryImpl customerRepository;
    private final ParserService parserService;
    private final NumberService numberService;

    public void saveCustomers() {
        try {
            File file = new File(PATHNAME);
            byte[] customerBytes = Files.readAllBytes(file.toPath());
            List<Customer> customer = parserService.deserialise(customerBytes, Customer.class);
            // Считаем рейтинг покупателей
                Map<String, Integer> ratings = ratingRepository.findAllRatings()
                        .stream()
                        .collect(Collectors.toMap(Rating::getName, Rating::getValue));
                customer.forEach(el -> {
                    if (ratings.containsKey(el.getStatus())) {
                        el.setRating(el.getNumberOfPurchases() - el.getNumberOfReturns() + ratings.get(el.getStatus()));
                    } else {
                        log.warn("Has not found the status. Check the statuses dictionary");
                    }
                });
                int[] ints = customerRepository.updateCustomers(customer);
                log.info("Update customers size: {}", ints.length);
                numberService.saveNumbers(customer);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Exception while trying to map JSON in method: save(), message {}", e.getMessage());
        }
    }
}