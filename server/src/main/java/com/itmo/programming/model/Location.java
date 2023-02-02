package com.itmo.programming.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Модель для локации
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Location implements Comparable<Location> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double x;
    private Double y; //Поле не может быть null
    private double z;
    private String name; //Поле не может быть null

    @Override
    public String toString() {
        return "Локация:" + "\n" +
                "x: " + x + "\n" +
                "y: " + y + "\n" +
                "z: " + z + "\n" +
                "имя локации: " + name;
    }

    @Override
    public int compareTo(Location o) {
        double volume = x * y * z;
        double volume1 = o.x * o.y * o.z;
        if (volume > volume1) {
            return 1;
        } else if (volume1 > volume) {
            return -1;
        }
        return 0;
    }
}
