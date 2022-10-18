package com.github.howtobegin.websocket.listener;

import com.github.howtobegin.websocket.WebSocketCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * @author lhl
 * @date 2022/10/18 上午10:28
 */
@Component
public class WebSocketDisconnectListener implements ApplicationListener<SessionDisconnectEvent> {

    private WebSocketCounter counter;

    @Autowired
    public WebSocketDisconnectListener(WebSocketCounter counter) {
        this.counter = counter;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        System.out.println("sessionId：" + sessionId + "已断开");
        counter.decrement();
    }
}
