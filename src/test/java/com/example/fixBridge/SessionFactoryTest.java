package com.example.fixBridge;

import com.example.fixBridge.models.enums.SessionType;
import com.example.fixBridge.services.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quickfix.ConfigError;
import quickfix.SessionID;
import quickfix.SessionSettings;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessionFactoryTest {

    private SessionSettings settings;
    private SessionFactory sessionSelector;

    private SessionID pricingSession = new SessionID("FIX.4.4", "MD_Manish_FIX", "CENTROID_SOL");
    private SessionID tradingSession = new SessionID("FIX.4.4", "TD_Manish_FIX", "CENTROID_SOL");

    @BeforeEach
    void setUp() throws ConfigError {
        settings = mock(SessionSettings.class);

        Iterator<SessionID> iterator = Arrays.asList(pricingSession, tradingSession).iterator();
        when(settings.sectionIterator()).thenReturn(iterator);

        when(settings.getString(pricingSession, "SenderCompID")).thenReturn("MD_Manish_FIX");
        when(settings.getString(tradingSession, "SenderCompID")).thenReturn("TD_Manish_FIX");

        sessionSelector = new SessionFactory(settings);
    }

    @Test
    void testSelectPricingSession_WhenInverseTrue() throws ConfigError {
        when(settings.getString(tradingSession, "SessionType")).thenReturn(SessionType.TRADING.toString());
        when(settings.getString(pricingSession, "SessionType")).thenReturn(SessionType.PRICING.toString());

        SessionID sessionId = sessionSelector.getTradingSession();

        assertEquals(tradingSession, sessionId);
    }


    @Test
    void testNoSession_ThrowsException() throws ConfigError {
        when(settings.getString(any(SessionID.class), eq("SenderCompID"))).thenReturn("UNKNOWN");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> sessionSelector.getTradingSession());

        assertEquals("No matching trading session found", ex.getMessage());
    }
}
