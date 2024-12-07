package cn.messageplus.core.server;

import org.springframework.context.annotation.ComponentScan;

/**
 * @author mo
 * @日期: 2024-12-03 23:33
 **/
@ComponentScan({
        "cn.messageplus.core.server.request",
        "cn.messageplus.core.server.response",
        "cn.messageplus.core.server.controller",
        "cn.messageplus.core.server.handler"
})
public class Config {
}
