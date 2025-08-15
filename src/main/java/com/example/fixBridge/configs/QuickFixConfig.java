package com.example.fixBridge.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import quickfix.*;
import java.io.InputStream;

@Configuration
public class QuickFixConfig {

    @Bean
    public SessionSettings sessionSettings() throws ConfigError {
        InputStream inputStream = getClass().getResourceAsStream("/quickfixLmax.cfg");
        return new SessionSettings(inputStream);
    }

    @Bean
    public MessageStoreFactory messageStoreFactory(SessionSettings settings) {
        return new FileStoreFactory(settings);
    }

    @Bean
    public LogFactory logFactory(SessionSettings settings) {
        return new FileLogFactory(settings);
    }

    @Bean
    public MessageFactory messageFactory() {
        return new DefaultMessageFactory();
    }
}
