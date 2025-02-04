package it.sisal.kairos.monitor.scheduler;

import it.sisal.kairos.monitor.service.MonitorServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnProperty(name = "client-monitoring.enabled", havingValue = "true")
public class ClientPingScheduler {

    public ClientPingScheduler(@Value("${client-monitoring.check-interval}") long checkInterval,
                               MonitorServiceImpl monitorServiceImpl) {
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(() -> monitorServiceImpl.checkActiveClients(checkInterval), 0, checkInterval,
                        TimeUnit.MILLISECONDS);
    }
}
