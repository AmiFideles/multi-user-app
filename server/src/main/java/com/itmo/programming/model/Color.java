package com.itmo.programming.model;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * enum для Цвета
 */
public enum Color {
    GREEN("зеленый"), BLACK("черный"), YELLOW("желтый"), ORANGE("оранжевый"), WHITE("белый");
    private final String color;

    Color(String color) {
        this.color = color;
    }

    public static boolean isPresent(String color) {
        return Arrays.stream(Color.values()).anyMatch(element -> element.color.equals(color));
    }

    public static Color getColorByString(String enumType) {
        return Arrays.stream(Color.values()).filter(element -> element.color.equals(enumType)).collect(Collectors.toList()).get(0);
    }

    public String getColor() {
        return color;
    }


}
