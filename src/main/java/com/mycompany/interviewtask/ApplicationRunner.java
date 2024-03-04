package com.mycompany.interviewtask;

import com.mycompany.interviewtask.service.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;

/**
 * Думаю что более правильно использовать ивенты, но ее как и постконстракт лучше в данном случае отключать перед тестами,
 * особенно интеграционынми.
 *
 */
@Repository
@RequiredArgsConstructor
public class ApplicationRunner {

    private final CustomerServiceImpl saveCustomers;

    @EventListener(ApplicationReadyEvent.class)
    public void start() {
        System.out.println("Start processing...");
        saveCustomers.saveCustomers();
        System.out.println("Finished");
    }
}
