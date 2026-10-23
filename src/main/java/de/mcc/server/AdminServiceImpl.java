package de.mcc.server;

import de.mcc.Ack;
import de.mcc.AdminServiceGrpc;
import de.mcc.DeleteRequest;
import io.grpc.stub.StreamObserver;

public class AdminServiceImpl extends AdminServiceGrpc.AdminServiceImplBase {
    private final ChatStore store;
    public AdminServiceImpl(ChatStore store){
        this.store=store;
    }

    public void deleteMessage(DeleteRequest request, StreamObserver<Ack> responseObserver){
        boolean ok=store.loeschen(request.getId());
        responseObserver.onNext(Ack.newBuilder()
                .setOk(ok)
                .build());
        responseObserver.onCompleted();
    }
}
