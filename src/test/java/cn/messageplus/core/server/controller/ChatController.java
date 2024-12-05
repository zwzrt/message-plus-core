package cn.messageplus.core.server.controller;

import cn.messageplus.core.annotation.MessagePlusMapping;
import cn.messageplus.core.annotation.MessagePlusRequest;

/**
 * 聊天
 **/
@MessagePlusMapping("/test")
public class ChatController {
    @MessagePlusMapping("/1")
    public void test1() {
        System.out.println("/test/1");
    }
    @MessagePlusMapping("/2")
    public void test2() {
        System.out.println("/test/2");
    }
}
