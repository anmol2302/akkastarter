package com.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Status;
import akka.testkit.TestActorRef;
import akka.testkit.TestProbe;
import com.example.messages.Connected;
import com.example.messages.GetRequest;
import com.example.messages.KeysNotFoundException;
import com.example.messages.SetRequest;
import com.typesafe.config.ConfigFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AkkademyTest {
    ActorSystem system = ActorSystem.create("system", ConfigFactory.empty());


    @Test
    public void isTestingConnection() {
        TestProbe testProbe = TestProbe.apply(system);
        TestActorRef<Akkademy> testActorRef = TestActorRef.create(system, Props.create(Akkademy.class));
        Akkademy akkademy = testActorRef.underlyingActor();
        testActorRef.tell(new Connected("connected", testProbe.ref()), ActorRef.noSender());
        testProbe.expectMsg("connected");
    }

    @Test
    public void shouldPlaceValueToMap() {
        TestProbe testProbe = TestProbe.apply(system);
        TestActorRef<Akkademy> actorRef = TestActorRef.create(system, Props.create(Akkademy.class));
        Akkademy akkademy = actorRef.underlyingActor();
        actorRef.tell(new SetRequest("key", "value", testProbe.ref()), ActorRef.noSender());
        testProbe.expectMsg(new Status.Success("key"));
    }

    @Test
    public void shouldPlaceMultipleValueToMap() {
        TestProbe testProbe = TestProbe.apply(system);
        TestActorRef<Akkademy> actorRef = TestActorRef.create(system, Props.create(Akkademy.class));
        Akkademy akkademy = actorRef.underlyingActor();
        List requests = Arrays.asList(new SetRequest("hello", "World", testProbe.ref()), new SetRequest("hi", "hy", testProbe.ref()));
        actorRef.tell(requests, ActorRef.noSender());
        testProbe.expectMsg(new Status.Success("hello"));
        testProbe.expectMsg(new Status.Success("hi"));
        actorRef.tell(new GetRequest("hello", testProbe.ref()), ActorRef.noSender());
        testProbe.expectMsg("World");
    }

    @Test
    public void checkForException() {
        TestProbe testProbe = TestProbe.apply(system);
        TestActorRef<Akkademy> actorRef = TestActorRef.create(system, Props.create(Akkademy.class));
        Akkademy akkademy = actorRef.underlyingActor();
        actorRef.tell(new GetRequest("he", testProbe.ref()), ActorRef.noSender());
        testProbe.expectMsgClass(Status.Failure.class);
    }
}
