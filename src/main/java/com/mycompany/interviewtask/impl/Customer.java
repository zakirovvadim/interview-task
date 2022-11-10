package com.mycompany.interviewtask.impl;

import lombok.Data;

@Data
@SuppressWarnings("all")
public class Customer {

    String firstName;
    String lastName;
    String status;
    Integer numberOfPurchases;
    Integer numberOfReturns;
    String phoneNumber;
    Integer rating;

}
