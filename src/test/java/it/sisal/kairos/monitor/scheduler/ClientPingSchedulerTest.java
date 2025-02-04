package it.sisal.kairos.monitor.scheduler;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.sisal.kairos.monitor.service.MonitorServiceImpl;

@ExtendWith(MockitoExtension.class)
class ClientPingSchedulerTest {

    @Mock
    private MonitorServiceImpl monitorServiceImpl;

    private static final long CHECK_INTERVAL = 1000L;

    @BeforeEach
    void setUp() {
        new ClientPingScheduler(CHECK_INTERVAL, monitorServiceImpl);
    }

    @Test
    void testSchedulerInvokesCheckActiveClients() {
        await()
            .atMost(CHECK_INTERVAL + 500, TimeUnit.MILLISECONDS)
            .untilAsserted(() -> verify(monitorServiceImpl, atLeastOnce()).checkActiveClients(CHECK_INTERVAL));
    }
}
