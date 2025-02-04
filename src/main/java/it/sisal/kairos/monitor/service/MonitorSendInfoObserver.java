package it.sisal.kairos.monitor.service;

import io.grpc.stub.StreamObserver;
import it.sisal.kairos.common.model.Client;
import it.sisal.kairos.common.util.MsgConstants;
import it.sisal.kairos.grpc.AckResponse;
import it.sisal.kairos.grpc.MessageRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentMap;

@Slf4j
public class MonitorSendInfoObserver implements StreamObserver<MessageRequest> {

    private final StreamObserver<AckResponse> responseObserver;
    private final ConcurrentMap<Client, StreamObserver<AckResponse>> clients;

    public MonitorSendInfoObserver(StreamObserver<AckResponse> responseObserver,
                                 ConcurrentMap<Client, StreamObserver<AckResponse>> clients) {
        this.responseObserver = responseObserver;
        this.clients = clients;
    }

    @Override
    public void onNext(MessageRequest messageRequest) {
        // Handle the client's message
        log.info("Received [{}] message from [{}]", messageRequest.getId(), messageRequest.getCaller());

        // Register the client
        registerClient(messageRequest.getCaller());

        // TODO: manage the messageRequest

        if (!messageRequest.getId().equals(MsgConstants.MSG_ID_PONG)) {
            // Send an ack to the client
            AckResponse response =
                    AckResponse.newBuilder().setSuccess(true).setRecordId(messageRequest.getId()).build();
            this.responseObserver.onNext(response);
        }
    }

    private void registerClient(String clientId) {
        // Register the client
        Client client = new Client(clientId);
        this.clients.put(client, this.responseObserver);
    }

    @Override
    public void onError(Throwable throwable) {
        // Handle any errors
    }

    @Override
    public void onCompleted() {
        // Complete the response stream
        this.responseObserver.onCompleted();
    }

}
