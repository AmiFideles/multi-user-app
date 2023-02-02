package com.itmo.programming.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Модель координат
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coordinate")
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long x;
    private Double y;


    @Override
    public String toString() {
        return "Координаты: " + "\n" +
                "x: " + x + "\n" +
                "y: " + y;
    }
}
