package com.definesys.readword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.definesys.mpaas", "com.definesys.readword"})
public class ReadwordApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadwordApplication.class, args);
    }

}
