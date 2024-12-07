
[![](./doc/img/logo_max_white.png)](https://www.red-coral.cn/)

# 消息增强器核心（message-plus-core）

<a href='https://gitee.com/modmb/message-plus-core/stargazers' style="margin-right: 5px">
    <img src='https://gitee.com/modmb/message-plus-core/badge/star.svg?theme=dark' alt='star'></img>
</a>
<a href='https://gitee.com/modmb/message-plus-core/members' style="margin-right: 5px">
    <img src='https://gitee.com/modmb/message-plus-core/badge/fork.svg?theme=dark' alt='fork'></img>
</a>
<img src="https://img.shields.io/static/v1?label=Github&message=message-plus&color=orange" alt="">

---

<a href="https://www.red-coral.cn/">前往主页</a>

[//]: # (&ensp;|&ensp;)
[//]: # (<a href="https://zwzrt.github.io/">加入我们</a>)

### 介绍

基于netty的消息增强器核心，帮助开发者快速开发 即时聊天系统。

功能：

   1. 支持自定义请求类型。
   2. 支持单发、群发、聊天室及系统功能。
   3. 以及数据持久化。
   4. 支持失败消息的持久化及重发功能。

### 软件架构

Maven + SpringBoot + Hutool + Netty

### 通信协议

|  名称  |   类型   |  长度   |
|:----:|:------:|:-----:|
| 协议版本 |  byte  | 1byte |
| 请求类型 | short  | 2byte |
| 内容长度 |  int   | 4byte |
|  内容  | string |   *   |

### 快速上手

1、在启动类加上@EnableMessagePlusCore注解即可启动消息增强器核心：

使用Java代码连接可以查看test下的ClientTest，其它语言的连接方式请自行查询。

2、如何创建自己的请求类：

以自带的ChatRequest请求类为例，首先需要添加@MessagePlusRequest注解（同时需要该类被Bean容器扫描到），然后继承MessageRequest抽象类并实现其抽象方法（自定义请求类型字节码需要大于等于100）。

```java
@MessagePlusRequest
public class ChatRequest extends MessageRequest {
    String content;

    public ChatRequest() {}
    public ChatRequest(String content) {
        this.content = content;
    }

    @Override
    public byte getType() {
        return 1;
    }
}
```

注意：请求类必须存在无参构造方法。

### 使用说明

1. 如果使用过程出现bug或者存在不足，可以向red_coral20240606@163.com发送邮箱，我们将会积极修复并提供更强大的功能。
