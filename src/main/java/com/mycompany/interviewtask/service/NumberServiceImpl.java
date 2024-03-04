package com.mycompany.interviewtask.service;

import com.mycompany.interviewtask.impl.Customer;
import com.mycompany.interviewtask.service.interfaces.NumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NumberServiceImpl implements NumberService {

    private final FileServiceImpl fileService;

    public void saveNumbers(List<Customer> customer) {
        // Здесь мы отбираем телефонные номера
        List<String> numbers = customer.stream()
                .map(Customer::getPhoneNumber)
                .filter(el -> !el.startsWith("+7"))
                .sorted()
                .collect(Collectors.toList());
        // Сохраняем базу телефонным номеров в файл
        fileService.saveNumbersToFile(numbers);
    }
}
