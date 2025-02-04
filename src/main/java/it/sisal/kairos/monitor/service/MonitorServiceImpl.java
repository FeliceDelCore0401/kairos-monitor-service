package it.sisal.kairos.monitor.service;

import io.grpc.stub.StreamObserver;
import it.sisal.kairos.common.GrpcServer;
import it.sisal.kairos.common.model.Client;
import it.sisal.kairos.grpc.AckResponse;
import it.sisal.kairos.grpc.MessageRequest;
import it.sisal.kairos.grpc.MonitorServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@GrpcService
public class MonitorServiceImpl extends MonitorServiceGrpc.MonitorServiceImplBase implements GrpcServer {

    private final ConcurrentMap<Client, StreamObserver<AckResponse>> clients = new ConcurrentHashMap<>();

    @Override
    public StreamObserver<MessageRequest> sendMonitorMessage(StreamObserver<AckResponse> responseObserver) {
        return new MonitorStreamObserver(responseObserver);
    }
    
    @Override
    public StreamObserver<MessageRequest> sendInfoMessage(StreamObserver<AckResponse> responseObserver) {
        return new MonitorSendInfoObserver(responseObserver, this.clients);
    }

    public void checkActiveClients(long checkInterval) {
        // default method
        checkActiveClients(this.clients, checkInterval, log);
    }

}
