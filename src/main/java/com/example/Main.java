package com.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

class Main {
    public static void main(String[] args) {
        ActorSystem system=ActorSystem.create("LocalSystem");
        system.actorOf(Props.create(Akkademy.class),"dbactor");
    }
}
