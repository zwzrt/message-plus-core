package cn.messageplus.core.server;

import cn.messageplus.core.MessagePlusAgreement;
import cn.messageplus.core.EnableMessagePlusCore;
import cn.messageplus.core.handler.LoginHandler;
import cn.messageplus.core.message.response.LoginResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private static final Map<String, String> accountMap = new ConcurrentHashMap<>();
    static {
        accountMap.put("admin", "admin");
        accountMap.put("张三", "123456");
    }
    @Bean
    public LoginHandler loginHandler() {
        return new LoginHandler() {
            @Override
            public String login(String username, String password) {
                String pass = accountMap.get(username);
                if (pass == null) {
                    return null;
                } else {
                    if (pass.equals(pass)) {
                        return username;
                    } else {
                        return null;
                    }
                }
            }
        };
    }
}
