package com.mycompany.interviewtask.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.mycompany.interviewtask.service.interfaces.ParserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Постарался сделать универсальный десериализатор.
 */
@Slf4j
@Service
public class ParserServiceImpl implements ParserService {

    public <T> List<T> deserialise(byte[] path, Class<T> expectedClass) throws IOException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(expectedClass);
        ObjectMapper mapper = new ObjectMapper();
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, expectedClass);
        return mapper.readValue(path, listType);
    }
}