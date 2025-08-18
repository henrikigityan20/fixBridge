package com.example.fixBridge.configs;

import org.springframework.core.io.Resource;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import quickfix.*;
import java.io.InputStream;

@Configuration
public class QuickFixConfig {

    @Value("${quickfix.config-file}")
    private Resource configFile;

    @Bean
    public SessionSettings sessionSettings() throws IOException, ConfigError {
        try (InputStream inputStream = configFile.getInputStream()) {
            return new SessionSettings(inputStream);
        }
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
