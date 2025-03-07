package cn.messageplus.core;

import cn.messageplus.core.utils.exterior.SpringUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Inherited
@Target({ElementType.TYPE})
@Import({
        SpringUtils.class,
        StartCore.class,
})
@ComponentScan("cn.messageplus.core.message")
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableMessagePlusCore {
    MessagePlusAgreement value() default MessagePlusAgreement.MPCA;
}
