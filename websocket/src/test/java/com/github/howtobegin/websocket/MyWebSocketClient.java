package com.github.howtobegin.websocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * @author lhl
 * @date 2022/10/18 上午12:10
 */
public class MyWebSocketClient extends WebSocketClient {
    public MyWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("open");
    }

    @Override
    public void onMessage(String s) {
        System.out.println("客户端收到的消息：" + s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {
        System.out.println("error:" + e.getMessage());
        e.printStackTrace();
    }
}
