package cn.messageplus.core.server;

import cn.messageplus.core.EnableMessagePlusCore;
import cn.messageplus.core.annotation.MessagePlusRequest;
import cn.messageplus.core.server.request.LoginRequest;
import cn.messageplus.core.utils.exterior.SpringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.util.Map;

/**
 * 服务端
 **/
@EnableMessagePlusCore
@SpringBootApplication()
@Import(Config.class)
public class ApplicationTest {
    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationTest.class, args);

        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(MessagePlusRequest.class);
        System.out.println(beansWithAnnotation);
        System.out.println(SpringUtils.getBeansWithAnnotation(MessagePlusRequest.class));

        System.in.read();
    }
}
