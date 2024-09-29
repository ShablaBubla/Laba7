package com.bubla.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    private String response;
    private boolean isRunning = true;

    public Response(String response){
        this.response = response;
    }
}
