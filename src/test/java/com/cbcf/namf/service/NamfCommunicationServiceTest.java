package com.cbcf.namf.service;

import com.cbcf.namf.model.N2InfoSubscribeRequest;
import com.cbcf.namf.model.N2MessageTransferRequest;
import com.cbcf.namf.repository.N2InfoSubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NamfCommunicationServiceTest {

    @Mock
    N2InfoSubscriptionRepository repo;

    @InjectMocks
    NamfCommunicationService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void transferNonUeN2Message_error() {
        N2MessageTransferRequest req = new N2MessageTransferRequest();
        req.setMessage("");
        var resp = service.transferNonUeN2Message(req).block();
        assertFalse(resp.isSuccess());
    }

    @Test
    void subscribeToN2Info_success() {
        N2InfoSubscribeRequest req = new N2InfoSubscribeRequest();
        req.setN2NotifyCallbackUri("http://localhost/notify");
        req.setN2InfoClass("SM");
        req.setN2InfoType(Collections.singletonList("TYPE1"));
        var resp = service.subscribeToN2Info(req);
        assertTrue(resp.isSuccess());
        assertNotNull(resp.getData());
        verify(repo, times(1)).save(any());
    }

    @Test
    void unsubscribeFromN2Info_notFound() {
        when(repo.findBySubscriptionId("notfound")).thenReturn(java.util.Optional.empty());
        var resp = service.unsubscribeFromN2Info("notfound");
        assertFalse(resp.isSuccess());
    }
}
