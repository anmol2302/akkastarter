package com.example;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Status;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import com.example.messages.Connected;
import com.example.messages.GetRequest;
import com.example.messages.KeysNotFoundException;
import com.example.messages.SetRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Akkademy extends AbstractActor {

    protected final LoggingAdapter logger = Logging.getLogger(context().system(), this);
    protected Map<String, String> map = new HashMap<>();

    public Akkademy() {
        receive(
                ReceiveBuilder.match(Connected.class, message -> {
                    message.actorRef.tell(message.getMessage(), self());
                }).match(List.class, message -> {
                    message.forEach(mes -> {
                        if (mes instanceof SetRequest) {
                            SetRequest setRequest = (SetRequest) mes;
                            handelSetRequest(setRequest);
                        }
                        if (mes instanceof GetRequest) {
                            GetRequest getRequest = (GetRequest) mes;
                            handelGetRequest(getRequest);
                        }
                    });

                }).match(SetRequest.class, message -> {
                    handelSetRequest(message);

                }).match(GetRequest.class, message -> {
                    handelGetRequest(message);
                }).matchAny(o -> {
                    sender().tell(new ClassNotFoundException("ignored request"), self());
                }).build());
    }
    private void handelGetRequest(GetRequest getRequest) {
        logger.info("Received Get request: {}", getRequest);
        String value = map.get(getRequest.key);
        Object response = (value==""|| value==null)?new Status.Failure(new KeysNotFoundException(getRequest.key)):value;
        getRequest.sender.tell(response, self());
    }

    private void handelSetRequest(SetRequest message) {
        map.put(message.key, message.value);
        message.sender.tell(new Status.Success(message.key), self());
    }


}
