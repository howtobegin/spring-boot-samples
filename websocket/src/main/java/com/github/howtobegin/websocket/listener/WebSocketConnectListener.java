package com.github.howtobegin.websocket.listener;

import com.github.howtobegin.websocket.WebSocketCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

/**
 * @author lhl
 * @date 2022/10/18 上午10:27
 */
@Component
public class WebSocketConnectListener implements ApplicationListener<SessionConnectEvent> {

    private WebSocketCounter counter;

    @Autowired
    public WebSocketConnectListener(WebSocketCounter counter) {
        this.counter = counter;
    }

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        System.out.println("sessionId：" + sessionId + "已连接");
        counter.increment();
    }
}
