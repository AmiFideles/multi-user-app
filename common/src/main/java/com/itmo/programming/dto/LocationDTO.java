package com.itmo.programming.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.Value;

import java.io.Serializable;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Data
@AllArgsConstructor
public class LocationDTO  implements Serializable {
    private static final long serialVersionUID = -6429137499550461110L;
    private double x;
    private Double y; //Поле не может быть null
    private double z;
    private String name; //Поле не может быть null


}
