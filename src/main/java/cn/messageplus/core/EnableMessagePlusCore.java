package cn.messageplus.core;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Import(StartCore.class)
public @interface EnableMessagePlusCore {
}
