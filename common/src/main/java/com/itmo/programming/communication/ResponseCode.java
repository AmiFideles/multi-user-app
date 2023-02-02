package com.itmo.programming.communication;

import java.io.Serializable;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public enum ResponseCode implements Serializable {
    CONNECTED,
    DATA_SIZE,
    AUTHORIZED
}
