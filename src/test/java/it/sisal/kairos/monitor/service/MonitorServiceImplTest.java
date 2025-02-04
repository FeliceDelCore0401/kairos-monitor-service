package it.sisal.kairos.monitor.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.grpc.stub.StreamObserver;
import it.sisal.kairos.grpc.AckResponse;
import it.sisal.kairos.grpc.MessageRequest;

class MonitorServiceImplTest {

    private MonitorServiceImpl monitorService;
    private StreamObserver<AckResponse> responseObserver;

    @BeforeEach
    void setUp() {
        monitorService = new MonitorServiceImpl();
        responseObserver = mock(StreamObserver.class);
    }

    @Test
    void shouldReturnNonNullRequestObserverForRegulatoryRestMessage() {
        StreamObserver<MessageRequest> requestObserver = monitorService.sendMonitorMessage(responseObserver);
        assertNotNull(requestObserver, "Request observer must not be null");
    }

    @Test
    void shouldReturnNonNullRequestObserverForInfoMessage() {
        StreamObserver<MessageRequest> requestObserver = monitorService.sendInfoMessage(responseObserver);
        assertNotNull(requestObserver, "Request observer must not be null");
    }
}
