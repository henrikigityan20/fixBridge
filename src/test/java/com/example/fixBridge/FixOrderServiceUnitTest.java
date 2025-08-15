package com.example.fixBridge;

import com.example.fixBridge.models.enums.CustomOrdType;
import com.example.fixBridge.models.enums.CustomSide;
import com.example.fixBridge.models.FixOrderResult;
import com.example.fixBridge.models.OrderRequest;
import com.example.fixBridge.services.FixOrderService;
import com.example.fixBridge.services.SessionFactory;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import quickfix.Session;
import quickfix.SessionID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.MockedStatic;

class FixOrderServiceUnitTest {

    @Mock
    private SessionFactory sessionFactory;

    @InjectMocks
    private FixOrderService fixOrderService;

    private MockedStatic<Session> mockedSession;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // mock static method Session.sendToTarget
        mockedSession = mockStatic(Session.class);
    }

    @AfterEach
    void tearDown() {
        mockedSession.close();
    }

    @Test
    void testSendFixOrder_Success() throws Exception {
        OrderRequest request = new OrderRequest("TSLA", 1, CustomSide.BUY, CustomOrdType.MARKET, false);
        SessionID mockSession = new SessionID("FIX.4.4", "TD_Manish_FIX", "CENTROID_SOL");

        when(sessionFactory.getTradingSession()).thenReturn(mockSession);

        // mock static call to return true
        mockedSession.when(() -> Session.sendToTarget(any(), eq(mockSession)))
            .thenReturn(true);

        FixOrderResult result = fixOrderService.sendFixOrder(request);

        mockedSession.verify(() -> Session.sendToTarget(any(), eq(mockSession)), times(1));

        assertTrue(result.success());
        assertEquals(mockSession.getSenderCompID(), result.sessionId());
        assertNotNull(result.orderId());
        assertNull(result.errorMessage());
    }

    @Test
    void testSendFixOrder_Failure() throws Exception {
        OrderRequest request = new OrderRequest("TSLA", 1, CustomSide.BUY, CustomOrdType.MARKET, false);
        SessionID mockSession = new SessionID("FIX.4.4", "TD_Manish_FIX", "CENTROID_SOL");

        when(sessionFactory.getTradingSession()).thenReturn(mockSession);

        // mock static call to throw exception
        mockedSession.when(() -> Session.sendToTarget(any(), eq(mockSession)))
            .thenThrow(new RuntimeException("FIX send failed"));

        FixOrderResult result = fixOrderService.sendFixOrder(request);

        assertFalse(result.success());
        assertNull(result.sessionId());
        assertNull(result.orderId());
        assertEquals("FIX send failed", result.errorMessage());
    }
}
