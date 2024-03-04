package com.mycompany.interviewtask.service.interfaces;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.interviewtask.impl.Customer;

import java.io.IOException;
import java.util.List;

public interface ParserService {
    //    List<Customer> parseFromFile(ObjectMapper objectMapper);
    <T> List<T> deserialise(byte[] payload, Class<T> expectedClass) throws IOException;
}
