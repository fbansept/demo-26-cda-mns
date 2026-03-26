package edu.ban7.demo26cdamns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Demo26CdaMnsApplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo26CdaMnsApplication.class, args);
    }

}
