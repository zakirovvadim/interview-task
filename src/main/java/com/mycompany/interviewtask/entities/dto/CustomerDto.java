package com.mycompany.interviewtask.entities.dto;

import lombok.Data;

/**
 * dto хи в случае трансфера данных во вне, но здесь нет необходимости использовать, так как работа происходит внутри
 */
@Data
public class CustomerDto {
    private String firstName;
    private String lastName;
    private String status;
    private Integer numberOfPurchases;
    private Integer numberOfReturns;
    private String phoneNumber;
    private Integer rating;
}
