package com.example.msaccountreservation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsAccountReservationApplication {

    private static final Logger log = LoggerFactory.getLogger(MsAccountReservationApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(MsAccountReservationApplication.class, args);
    }

}
