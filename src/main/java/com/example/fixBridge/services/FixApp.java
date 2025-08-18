package com.example.fixBridge.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quickfix.*;
import quickfix.field.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class FixApp implements Application {

    private final Map<SessionID, Session> sessions = new ConcurrentHashMap<>();

    @Autowired
    private SessionSettings settings;

    @Override
    public void onCreate(SessionID sessionId) {
        log.info("onCreate for session: {}", sessionId);
        Session session = Session.lookupSession(sessionId);
        if (session != null) {
            sessions.put(sessionId, session);
        } else {
            log.warn("Requested session is not found.");
        }
    }

    @Override
    public void onLogon(SessionID sessionId) {
        log.info("Logon: {}", sessionId);
        sessions.put(sessionId, Session.lookupSession(sessionId));
    }

    @Override
    public void onLogout(SessionID sessionId) {
        log.info("onLogout for session: {}", sessionId);
        sessions.remove(sessionId);
    }

    @Override
    public void toAdmin(Message message, SessionID sessionId) {
        try {
            String msgType = message.getHeader().getString(MsgType.FIELD);

            if (msgType.equals(MsgType.LOGON)) {
                String username = settings.getString(sessionId, "Username");
                String password = settings.getString(sessionId, "Password");

                message.setString(Username.FIELD, username);
                message.setString(Password.FIELD, password);
            }
        } catch (FieldNotFound | ConfigError e) {
            log.error("Error setting logon fields for session: {}", sessionId, e);
        }
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionId) {
        log.info("Received admin message from {}: {}", sessionId, message);
    }

    @Override
    public void toApp(Message message, SessionID sessionId) throws DoNotSend {
        log.info("Sending message to {}: {}", sessionId, message);
    }

    @Override
    public void fromApp(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        log.info("fromApp received message type {}: {}", message.getHeader().getString(MsgType.FIELD), message);
    }
}
