package com.example.fixBridge.services;


import com.example.fixBridge.models.enums.SessionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import quickfix.*;

import java.util.Iterator;

@Component
@RequiredArgsConstructor
@Slf4j
public class SessionFactory {

    private final SessionSettings settings;

    private static final String SESSION_TYPE_KEY = "SessionType";


    public SessionID getTradingSession() {
        try {
            log.info("Searching for TRADING session in FIX settings...");
            Iterator<SessionID> it = settings.sectionIterator();
            while (it.hasNext()) {
                SessionID sessionId = it.next();
                String sessionTypeStr = settings.getString(sessionId, SESSION_TYPE_KEY);

                if (sessionTypeStr != null) {
                    SessionType sessionType = SessionType.valueOf(sessionTypeStr.toUpperCase());
                    if (sessionType == SessionType.TRADING) {
                        return sessionId;
                    }
                }
            }
            log.warn("No TRADING session found in FIX settings.");
        } catch (ConfigError e) {
            log.error("Error reading FIX settings", e);
            throw new RuntimeException("Error reading FIX settings", e);
        }

        throw new RuntimeException("No matching trading session found");
    }
}
