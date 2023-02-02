package com.itmo.programming.communication;


import lombok.Data;

import java.io.Serializable;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Data
public class Response implements Serializable {
    private static final long serialVersionUID = 5467842688395534629L;
    private ResponseBody responseBody;
    private ResponseCode responseCode;
    private int bufferSize;

    public Response(int bufferSize, ResponseCode responseCode) {
        this.bufferSize = bufferSize;
        this.responseCode = responseCode;
    }

    public Response(ResponseBody responseBody, ResponseCode responseCode) {
        this.responseBody = responseBody;
        this.responseCode = responseCode;
    }

    public Response(ResponseBody responseBody) {
        this.responseBody = responseBody;
    }

    public Response(ResponseCode responseCode){
        this.responseCode = responseCode;
    }
}
