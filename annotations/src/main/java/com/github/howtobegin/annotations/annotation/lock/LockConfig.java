package com.github.howtobegin.annotations.annotation.lock;

import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

/**
 * @author lhl
 * @date 2022/10/27 下午4:59
 */
@Configuration
public class LockConfig {
    @Bean
    public ExpressionParser expressionParser() {
        return new SpelExpressionParser();
    }

    @Bean
    public BeanResolver beanResolver(ApplicationContext applicationContext) {
        return new BeanFactoryResolver(applicationContext);
    }

    @Bean
    public RedissonAutoConfigurationCustomizer redissonAutoConfigurationCustomizer() {
        return config -> {
            if (StringUtils.isEmpty(config.useSingleServer().getPassword())) {
                config.useSingleServer().setPassword(null);
            }
        };
    }
}
