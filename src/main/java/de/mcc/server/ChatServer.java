package de.mcc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class ChatServer {
    public static void main(String[] args) throws Exception {
        ChatStore store=new ChatStore();
        Server server= ServerBuilder.forPort(50051)
                .addService(new ChatServiceImpl(store))
                .addService(new AdminServiceImpl(store))
                .build();

        server.start();
        System.out.println("Server laueft im Port 50051");
        server.awaitTermination();
    }

}
