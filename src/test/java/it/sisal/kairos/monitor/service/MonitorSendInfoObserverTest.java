package it.sisal.kairos.monitor.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.grpc.stub.StreamObserver;
import it.sisal.kairos.common.model.Client;
import it.sisal.kairos.grpc.AckResponse;

@ExtendWith(MockitoExtension.class)
class MonitorSendInfoObserverTest {

    @Mock
    private StreamObserver<AckResponse> responseObserver;

    private ConcurrentMap<Client, StreamObserver<AckResponse>> clients;
    private MonitorSendInfoObserver observer;

    @BeforeEach
    void setUp() {
        clients = new ConcurrentHashMap<>();
        observer = new MonitorSendInfoObserver(responseObserver, clients);
    }


    @Test
    void testOnError_ShouldHandleErrorGracefully() {
        Throwable throwable = new RuntimeException("Test Exception");

        assertDoesNotThrow(() -> observer.onError(throwable));
    }

    @Test
    void testOnCompleted_ShouldCompleteResponseObserver() {
        observer.onCompleted();

        verify(responseObserver).onCompleted();
    }
}
