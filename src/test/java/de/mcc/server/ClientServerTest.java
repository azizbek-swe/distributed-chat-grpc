package de.mcc.server;

import de.mcc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientServerTest {
    private ManagedChannel channel;

    class SendThread extends Thread{
        public void run(){
            ChatServiceGrpc.newBlockingStub(channel).sendMessage(SendRequest.newBuilder()
                    .setAuthor("t")
                    .setText("msg")
                    .build());
        }
    }
    class DeleteThread extends Thread{
        public void run(){
            AdminServiceGrpc.newBlockingStub(channel).deleteMessage(DeleteRequest.newBuilder()
                    .setId(-1)
                    .build());
        }
    }
    @Test
    void vieleClientsParallel() throws  Exception{
        ChatStore store=new ChatStore();
        Server server =ServerBuilder.forPort(50551)
                .addService(new ChatServiceImpl(store))
                .addService(new AdminServiceImpl(store))
                .build()
                .start();
        channel=ManagedChannelBuilder.forAddress("localhost", 50551)
                .usePlaintext().build();


        int a=50;
        Thread[] threads=new Thread[2*a];
        for (int i=0; i<a; i++){
            threads[2*i]=new SendThread();
            threads[2*i+1]=new DeleteThread();

        }
        for (Thread t: threads){
            t.start();
        }
        for (Thread t: threads){
            t.join();
        }

        MessageList list=ChatServiceGrpc.newBlockingStub(channel)
                .getMessage(Empty.newBuilder().build());
        assertEquals(a, list.getMessagesCount());

        channel.shutdown();
        server.shutdown();
    }
}
