package cn.messageplus.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author mo
 * @日期: 2024-11-30 23:34
 **/
@EnableMessagePlusCore
@SpringBootApplication
public class ApplicationTest {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationTest.class, args);
    }
}
