package com.itmo.programming.communication;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class ResponseBody implements Serializable {
    private static final long serialVersionUID = 8366430391664898665L;
    private ArrayList<String> responseList;

    public ResponseBody() {
        this.responseList = new ArrayList<>();
    }

    public void addCommandResponseBody(String response){
        responseList.add(response);
    }

    public String getData(){
        return String.join("\n", responseList);
    }
}
