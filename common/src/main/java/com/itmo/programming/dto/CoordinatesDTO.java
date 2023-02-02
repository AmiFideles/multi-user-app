package com.itmo.programming.dto;

import lombok.*;

import java.io.Serializable;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Data
@AllArgsConstructor
public class CoordinatesDTO implements Serializable {
    private static final long serialVersionUID = 7844927784338858621L;
    private Long x; //Поле не может быть null
    private Double y; //Значение поля должно быть больше -537

}
