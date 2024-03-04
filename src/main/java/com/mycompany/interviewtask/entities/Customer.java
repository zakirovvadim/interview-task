package com.mycompany.interviewtask.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Добавил приватные уровни и аннотации к идентификатору, ну и аннотации
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String status;
    private Integer numberOfPurchases;
    private Integer numberOfReturns;
    private String phoneNumber;
    private Integer rating;
}