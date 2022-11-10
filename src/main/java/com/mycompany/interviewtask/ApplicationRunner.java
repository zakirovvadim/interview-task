package com.mycompany.interviewtask;

import com.mycompany.interviewtask.impl.SaveCustomers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
@SuppressWarnings("all")
public class ApplicationRunner {

    SaveCustomers saveCustomers;


    @PostConstruct
    public void start() {
        System.out.println("Start processing...");
        saveCustomers.save();
        System.out.println("Finished");
    }

    @Autowired
    public void setSaveCustomers(SaveCustomers saveCustomers) {
        this.saveCustomers = saveCustomers;
    }
}
