package com.mycompany.interviewtask.repository.interfaces;

import com.mycompany.interviewtask.impl.Customer;

import java.util.List;

public interface CustomerRepository {
    int[] updateCustomers(List<Customer> customer);
}
