package com.example.fixBridge.services;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import quickfix.Application;
import quickfix.ConfigError;
import quickfix.Initiator;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;

@Component
@RequiredArgsConstructor
@Slf4j
public class FixEngineStarter {

    private final Application quickFixApp;
    private final SessionSettings sessionSettings;
    private final MessageStoreFactory messageStoreFactory;
    private final LogFactory logFactory;
    private final MessageFactory messageFactory;

    private Initiator initiator;

    @PostConstruct
    public void start() throws ConfigError {
        initiator = new SocketInitiator(
            quickFixApp,
            messageStoreFactory,
            sessionSettings,
            logFactory,
            messageFactory
        );
        initiator.start();
        log.info("FIX Engine started.");
    }

    @PreDestroy
    public void stop() {
        if (initiator != null) {
            initiator.stop();
            log.info("FIX Engine stopped.");
        }
    }
}
