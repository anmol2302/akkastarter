package com.example.messages;

import java.io.Serializable;

public class KeysNotFoundException extends Exception implements Serializable {

    public final String key;
    public KeysNotFoundException(String key) {
        this.key=key;
    }
}
