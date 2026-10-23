package de.mcc.client;

import de.mcc.Ack;
import de.mcc.AdminServiceGrpc;
import de.mcc.DeleteRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class AdminClient {
    public static void main(String[] args) {
        ManagedChannel channel= ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        AdminServiceGrpc.AdminServiceBlockingStub stub=AdminServiceGrpc.newBlockingStub(channel);
        Ack ack=stub.deleteMessage(DeleteRequest.newBuilder().setId(1).build());

        System.out.println("Geloescht: "+ ack.getOk());
        channel.shutdown();
    }
}
