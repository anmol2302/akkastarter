package com.example.messages;

import akka.actor.ActorRef;

import java.io.Serializable;

public class Connected implements Serializable {
    public final String message;
    public final ActorRef actorRef;

    public String getMessage() {
        return message;
    }

    public Connected(String message,ActorRef actorRef) {
        this.message = message;
        this.actorRef=actorRef;
    }
}
