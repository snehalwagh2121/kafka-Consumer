package com.example.kafka.kafkacontroller.component;

import com.example.kafka.kafkacontroller.model.Employee;
import com.google.gson.Gson;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    Gson g = new Gson();
    @KafkaListener(topics = "test", groupId = "0")
    public void consumer(String e){
        System.out.println("employee e = "+ e);
        Employee emp= g.fromJson(e, Employee.class);
        System.out.println(" employee object received: "+emp.getEid()+ ", "+emp.getEname()+", "+emp.getDept());
    }
}
