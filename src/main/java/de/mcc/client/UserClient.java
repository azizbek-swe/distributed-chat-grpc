package de.mcc.client;

import de.mcc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class UserClient {
    public static void main(String[] args) {
        ManagedChannel channel= ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        ChatServiceGrpc.ChatServiceBlockingStub stub=ChatServiceGrpc.newBlockingStub(channel);

        stub.sendMessage(SendRequest.newBuilder().setAuthor("Aziz")
                .setText("Hello!")
                .build());

        MessageList list=stub.getMessage(Empty.newBuilder().build());
        for(ChatMessage m: list.getMessagesList()){
            System.out.println(m.getId()+" | "+m.getAuthor()+": "+ m.getText() );

        }
        channel.shutdown();
    }
}
