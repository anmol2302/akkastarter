package com.example.messages;

import akka.actor.ActorRef;

public class SetRequest {

    public String key;
    public String value;
    public ActorRef sender;
    public SetRequest(String key, String value, ActorRef sender) {
        this.key = key;
        this.value = value;
        this.sender = sender;
    }

    public SetRequest(String key, String value) {
        this.key = key;
        this.value = value;
        this.sender = ActorRef.noSender();
    }

}
