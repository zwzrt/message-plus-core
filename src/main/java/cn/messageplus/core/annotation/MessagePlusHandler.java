package cn.messageplus.core.annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 消息处理器注解
 **/
@Configuration
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MessagePlusHandler {
}
