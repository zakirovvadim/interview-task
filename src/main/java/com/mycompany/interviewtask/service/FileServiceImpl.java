package com.mycompany.interviewtask.service;

import com.mycompany.interviewtask.service.interfaces.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Value("${file.number}")
    private String numberFilePath;

    public void saveNumbersToFile(List<String> numbers) {
        // Сохраняем базу телефонным номеров в файл
        try (FileWriter fileWriter = new FileWriter(numberFilePath)) {
            for (String number : numbers) {
                fileWriter.write(number + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Failed save numbers to file");
        }
    }
}
