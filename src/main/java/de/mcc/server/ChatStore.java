package de.mcc.server;

import de.mcc.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatStore {

    private final List<ChatMessage> message=new ArrayList<>();
    private int naechsteId=1;

    public synchronized ChatMessage hinzufuegen(String author, String text){
        ChatMessage m=ChatMessage.newBuilder()
                .setId(naechsteId++)
                .setAuthor(author)
                .setText(text)
                .build();
        message.add(m);
        return m;
    }

    public synchronized List<ChatMessage> alle(){
        return new ArrayList<>(message);
    }

    public synchronized boolean loeschen(int id){
        boolean gefunden=false;
        for(int i=0; i<message.size();i++){
            if(message.get(i).getId()==id){
                message.remove(i);
                gefunden=true;
                break;
            }
        }
        return gefunden;
    }
}
