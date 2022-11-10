package com.mycompany.interviewtask.impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Component
@SuppressWarnings("all")
public class SaveCustomers {

    JdbcTemplate template;

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public void save() {
        // Здесь мы парсим
        ObjectMapper objectMapper = new ObjectMapper();
        List customer = new LinkedList();
        try {
            customer = objectMapper.readValue(new File("src/main/resources/customers.json"), new TypeReference<List<Customer>>() {
            });
        } catch (Throwable e) {
        }

        // Здесь мы отбираем телефонные номера
        List<String> numbers = new LinkedList<>();
        for (int i = 0; i < customer.size(); i++) {
            String number = ((Customer) customer.get(i)).phoneNumber;
            if (number.startsWith("+7")) {
                continue;
            }
            numbers.add(number);
        }

        // Сортируем телефонные номера классическим пузырьком, алгоритм не самый быстрый, но выбор в сторону простоты
        boolean isSorted = false;
        String buf;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < numbers.size() - 1; i++) {
                if (numbers.get(i).compareTo(numbers.get(i + 1)) > 0) {
                    isSorted = false;
                    buf = numbers.get(i);
                    numbers.set(i, numbers.get(i + 1));
                    numbers.set(i + 1, buf);
                }
            }
        }

        // Считаем рейтинг покупателей
        for (int i = 0; i < customer.size(); i++) {
            Customer item = (Customer) customer.get(i);
            int rating = item.numberOfPurchases - item.numberOfReturns;
            if (item.status.equals("gold")) {
                rating = rating + 100;
            } else if (item.status.equals("plutinum")) {
                rating = rating + 1000;
            } else if (item.status.equals("silver")) {
                rating = rating + 10;
            }
            item.rating = rating;
        }

        // А здесь мы все сохраняем в БД
        String sql = "INSERT INTO "
                + "my_schema.customers "
                + "(first_name,last_name,phone_number,rating ) "
                + "VALUES " + "(?,?,?,?)";

        List finalList = customer;
        template.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                Customer item = (Customer) finalList.get(i);
                ps.setString(1, item.firstName);
                ps.setString(2, item.lastName);
                ps.setString(3, item.phoneNumber);
                ps.setInt(4, item.rating);
            }

            @Override
            public int getBatchSize() {
                return finalList.size();
            }
        });

        // Сохраняем базу телефонным номеров в файл
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("phone_numbers.txt");
        } catch (Exception e) {
        }
        for (int i = 0; i < numbers.size(); i++) {
            writer.print(numbers.get(i) + "\n");
        }
        writer.close();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaveCustomers that = (SaveCustomers) o;
        return Objects.equals(template, that.template);
    }

    @Override
    public int hashCode() {
        return Objects.hash(template);
    }
}
