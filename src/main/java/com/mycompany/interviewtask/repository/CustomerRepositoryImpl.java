package com.mycompany.interviewtask.repository;

import com.mycompany.interviewtask.impl.Customer;
import com.mycompany.interviewtask.repository.interfaces.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;


    public int[] updateCustomers(List<Customer> customer) {
        // А здесь мы все сохраняем в БД
        String sql = "INSERT INTO public.customer (first_name,last_name,phone_number,rating) VALUES (?,?,?,?)";
        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Customer item = customer.get(i);
                ps.setString(1, Optional.ofNullable(item.getFirstName()).orElse(""));
                ps.setString(2, Optional.ofNullable(item.getLastName()).orElse(""));
                ps.setString(3, Optional.ofNullable(item.getPhoneNumber()).orElse(""));
                ps.setInt(4, Optional.ofNullable(item.getRating()).orElse(0));
            }

            @Override
            public int getBatchSize() {
                return customer.size();
            }
        });
    }
}
