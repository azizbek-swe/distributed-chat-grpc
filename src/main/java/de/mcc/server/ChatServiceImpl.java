package de.mcc.server;

import de.mcc.*;
import io.grpc.stub.StreamObserver;

public class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {
    private final ChatStore store;
    public ChatServiceImpl(ChatStore store){
        this.store=store;
    }

    @Override
    public void sendMessage(SendRequest request, StreamObserver<Ack> responseObserver){
        store.hinzufuegen(request.getAuthor(), request.getText());
        responseObserver.onNext(Ack.newBuilder().setOk(true).build());
        responseObserver.onCompleted();
    }
    @Override
    public void getMessage(Empty request, StreamObserver<MessageList> responseObserver){
        MessageList list=MessageList.newBuilder()
                .addAllMessages(store.alle())
                .build();
        responseObserver.onNext(list);
        responseObserver.onCompleted();
    }
}
