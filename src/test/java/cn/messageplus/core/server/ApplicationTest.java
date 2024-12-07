package cn.messageplus.core.server;

import cn.messageplus.core.MessagePlusAgreement;
import cn.messageplus.core.EnableMessagePlusCore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import java.io.IOException;

/**
 * 服务端
 **/
@EnableMessagePlusCore(MessagePlusAgreement.MPCA)
@SpringBootApplication()
@Import(Config.class)
public class ApplicationTest {
    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationTest.class, args);

//        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(MessagePlusRequest.class);
//        System.out.println(beansWithAnnotation);
//        System.out.println(SpringUtils.getBeansWithAnnotation(MessagePlusRequest.class));

        System.in.read();
    }
}
