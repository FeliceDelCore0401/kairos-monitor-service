package it.sisal.kairos.monitor.service;

import io.grpc.stub.StreamObserver;
import it.sisal.kairos.grpc.AckResponse;
import it.sisal.kairos.grpc.MessageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MonitorStreamObserverTest {

    private StreamObserver<AckResponse> responseObserver;
    private MonitorStreamObserver monitorStreamObserver;

    @BeforeEach
    void setUp() {
        responseObserver = Mockito.mock(StreamObserver.class);
        monitorStreamObserver = new MonitorStreamObserver(responseObserver);
    }

    @Test
    void testOnNext() {
        MessageRequest messageRequest = MessageRequest.newBuilder()
                .setId("123")
                .setCaller("TestCaller")
                .build();

        assertDoesNotThrow(() -> monitorStreamObserver.onNext(messageRequest));
    }

    @Test
    void testOnError() {
        Throwable throwable = new RuntimeException("Test Exception");

        assertDoesNotThrow(() -> monitorStreamObserver.onError(throwable));
    }

    @Test
    void testOnCompleted() {
        monitorStreamObserver.onCompleted();

        verify(responseObserver, times(1)).onCompleted();
    }
}
