package com.itmo.programming.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Iskandarov Shakhzodbek P3133
 */

public enum ColorDTO implements Serializable {
    GREEN("зеленый"), BLACK("черный"), YELLOW("желтый"), ORANGE("оранжевый"), WHITE("белый");
    private String color;

    ColorDTO(String color) {
        this.color = color;
    }

    public static boolean isPresent(String color) {
        return Arrays.stream(ColorDTO.values()).anyMatch(element -> element.color.equals(color));
    }

    public static ColorDTO getColorByString(String enumType) {
        return Arrays.stream(ColorDTO.values()).filter(element -> element.color.equals(enumType)).collect(Collectors.toList()).get(0);
    }

    public String getColor() {
        return color;
    }
}
