package it.sisal.kairos.monitor.service;

import io.grpc.stub.StreamObserver;
import it.sisal.kairos.grpc.AckResponse;
import it.sisal.kairos.grpc.MessageRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MonitorStreamObserver implements StreamObserver<MessageRequest> {

    private final StreamObserver<AckResponse> responseObserver;

    public MonitorStreamObserver(StreamObserver<AckResponse> responseObserver) {
        this.responseObserver = responseObserver;
    }

    @Override
    public void onNext(MessageRequest messageRequest) {
        // Handle the client's message
        log.info("Received [{}] message from [{}]", messageRequest.getId(), messageRequest.getCaller());

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
