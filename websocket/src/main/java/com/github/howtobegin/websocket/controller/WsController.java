package com.github.howtobegin.websocket.controller;

import com.github.howtobegin.websocket.constant.Constant;
import com.github.howtobegin.websocket.message.WiselyMessage;
import com.github.howtobegin.websocket.message.WiselyResponse;
import com.github.howtobegin.websocket.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.broker.SimpleBrokerMessageHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lhl
 * @date 2022/10/17 下午11:24
 */
@Controller
public class WsController {

    @Resource
    WebSocketService webSocketService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    // welcome
    @MessageMapping(Constant.FORETOSERVERPATH)//@MessageMapping和@RequestMapping功能类似，用于设置URL映射地址，浏览器向服务器发起请求，需要通过该地址。
    // /topic/getResponse
    @SendTo(Constant.PRODUCERPATH)//如果服务器接受到了消息，就会对订阅了@SendTo括号中的地址传送消息。
    public WiselyResponse say(WiselyMessage message) throws Exception {
        List<String> users = new ArrayList<>();
        users.add("d892bf12bf7d11e793b69c5c8e6f60fb");//此处写死只是为了方便测试,此值需要对应页面中订阅个人消息的userId
        webSocketService.send2Users(users, new WiselyResponse("admin hello"));

        return new WiselyResponse("Welcome, " + message.getName() + "!");
    }

    /**
     * 订阅广播，服务器主动推给连接的客户端
     * 通过Http请求的方式触发订阅操作
     */
    @RequestMapping("/subscribeTopic")
    public void subscribeTopicByHttp() {
        while (true) {
            // 可以灵活设置成通道地址，实现发布订阅的功能
            String channel = "/topic/subscribeTopic";
            simpMessagingTemplate.convertAndSend(channel, Instant.now());
            try {
                Thread.sleep(3*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 订阅广播，服务器主动推给连接的客户端
     * 通过Websocket的subscribe操作触发订阅操作
     */
    @SubscribeMapping("/subscribeTopic")
    public Long subscribeTopicByWebSocket() {
        return Instant.now().toEpochMilli();
    }

    /**
     * 服务端接收客户端发送的消息，类似OnMessage方法
     */
    @MessageMapping("/sendToServer")
    public void handleMessage(String message) {
        System.out.println("message : " + message);
    }

    /**
     * 将客户端发送的消息广播出去
     */
    @MessageMapping("/sendToTopic")
    @SendTo("/topic/subscribeTopic")
    public String sendToTopic(String message) {
        return message;
    }
}
