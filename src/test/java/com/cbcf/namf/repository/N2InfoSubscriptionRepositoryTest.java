package com.cbcf.namf.repository;

import com.cbcf.namf.model.N2InfoSubscription;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class N2InfoSubscriptionRepositoryTest {

    @Autowired
    private N2InfoSubscriptionRepository repository;

    @Test
    void saveAndFindBySubscriptionId() {
        N2InfoSubscription sub = new N2InfoSubscription();
        sub.setSubscriptionId("sub-1");
        sub.setCallbackUri("http://cb");
        sub.setN2InfoClass("SM");
        sub.setN2InfoType("TYPE1");
        sub.setAmfInstanceId("amf-1");
        sub.setValidityTime(LocalDateTime.now().plusHours(1));
        sub.setActive(true);

        repository.save(sub);

        var found = repository.findBySubscriptionId("sub-1");
        assertTrue(found.isPresent());
        assertEquals("SM", found.get().getN2InfoClass());
    }
}
