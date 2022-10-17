package com.github.howtobegiin.redispubsub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @author lhl
 * @date 2022/10/17 下午9:47
 */
@Slf4j
@Configuration
public class RedisListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] bytes) {
        //获取订阅消息内容
        String topic = new String(bytes);
        String context = new String(message.getBody());
        log.info("topic:{},context:{}",topic,context);
    }

    @Bean
    RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory redisConnectionFactory, RedisListener redisListener) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        //订阅topic - subscribe
        redisMessageListenerContainer.addMessageListener(redisListener,new ChannelTopic("subscribe"));
        return redisMessageListenerContainer;
    }
}
