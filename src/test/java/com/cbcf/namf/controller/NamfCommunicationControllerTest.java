package com.cbcf.namf.controller;

import com.cbcf.namf.model.N2InfoSubscribeRequest;
import com.cbcf.namf.model.N2MessageTransferRequest;
import com.cbcf.namf.repository.N2InfoSubscriptionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NamfCommunicationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    N2InfoSubscriptionRepository repository;

    static String subscriptionId;

    @Test
    @Order(1)

    void transferNonUeN2Message_success() throws Exception {
        N2MessageTransferRequest req = new N2MessageTransferRequest();
        req.setMessage("Test Message");
        mockMvc.perform(post("/namf-comm/v1/non-ue-n2-messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @Order(2)
    void transferNonUeN2Message_error() throws Exception {
        N2MessageTransferRequest req = new N2MessageTransferRequest();
        req.setMessage("");
        mockMvc.perform(post("/namf-comm/v1/non-ue-n2-messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @Order(3)
    void subscribeToN2Info_success() throws Exception {
        N2InfoSubscribeRequest req = new N2InfoSubscribeRequest();
        req.setN2NotifyCallbackUri("http://localhost/notify");
        req.setN2InfoClass("SM");
        req.setN2InfoType(Collections.singletonList("TYPE1"));
        req.setAmfInstanceId("amf-1");
        req.setSubscriptionValidityTime(LocalDateTime.now().plusHours(1));
        String response = mockMvc.perform(post("/namf-comm/v1/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated()) // Expect 201 for creation
                .andExpect(jsonPath("$.success").value(true))
                .andReturn().getResponse().getContentAsString();

        // Extract subscriptionId from response
        subscriptionId = objectMapper.readTree(response).get("data").asText();
        Assertions.assertNotNull(subscriptionId);
    }

    @Test
    @Order(4)
    void subscribeToN2Info_missingFields() throws Exception {
        N2InfoSubscribeRequest req = new N2InfoSubscribeRequest();
        mockMvc.perform(post("/namf-comm/v1/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest()); // 400 for missing required fields
    }

    @Test
    @Order(5)
    void getActiveSubscriptions() throws Exception {
        mockMvc.perform(get("/namf-comm/v1/subscriptions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));
    }

    @Test
    @Order(6)
    void unsubscribeFromN2Info_success() throws Exception {
        mockMvc.perform(delete("/namf-comm/v1/subscriptions/" + subscriptionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @Order(7)
    void unsubscribeFromN2Info_notFound() throws Exception {
        mockMvc.perform(delete("/namf-comm/v1/subscriptions/invalid-id"))
                .andExpect(status().isNotFound()) // 404 for not found
                .andExpect(jsonPath("$.success").value(false));
    }
}
