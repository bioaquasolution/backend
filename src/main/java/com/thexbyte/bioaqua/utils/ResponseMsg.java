package com.thexbyte.bioaqua.utils;

 

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 

public class ResponseMsg {
    private String message;

    public ResponseMsg(String message) {
        this.message = message;
    }

    public ResponseMsg(   ) {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}