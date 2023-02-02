package com.itmo.programming.dto;

import com.itmo.programming.console.exceptions.InvalidInputException;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Data
@NoArgsConstructor
public class PersonDTO implements Serializable {
    private static final long serialVersionUID = 7613016340933649170L;
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long height; //Поле может быть null
    private java.time.LocalDate birthday; //Поле не может быть null
    private String passportID; //Поле может быть null
    private long key;
    private UserDTO userDTO;

    private CoordinatesDTO coordinatesDTO; //Поле не может быть null

    private LocationDTO locationDTO; //Поле не может быть null

    private ColorDTO colorDTO; //Поле не может быть null


    public PersonDTO(String name, CoordinatesDTO coordinatesDTO, Long height, LocalDate birthday, String passportID, ColorDTO colorDTO, LocationDTO locationDTO) {
        this.name = name;
        this.coordinatesDTO = coordinatesDTO;
        this.creationDate = Date.from(Instant.now());
        this.height = height;
        this.birthday = birthday;
        this.passportID = passportID;
        this.colorDTO = colorDTO;
        this.locationDTO = locationDTO;
    }

    public PersonDTO(String name, long id, CoordinatesDTO coordinatesDTO, Long height, LocalDate birthday, String passportID, ColorDTO colorDTO, LocationDTO locationDTO) {
        this.name = name;
        this.id=id;
        this.coordinatesDTO = coordinatesDTO;
        this.creationDate = Date.from(Instant.now());
        this.height = height;
        this.birthday = birthday;
        this.passportID = passportID;
        this.colorDTO = colorDTO;
        this.locationDTO = locationDTO;
    }
    public void checkFields() {
        StringBuilder sb = new StringBuilder();
        if (id <= 0) {
            sb.append("Значения id у человека не может быть меньше 0").append("\n");
        }
        if (name == null) {
            sb.append("Значения имени не может быть null").append("\n");
        }
        if (coordinatesDTO == null) {
            sb.append("Значения координат не может быть null").append("\n");
        }else {
            if (coordinatesDTO.getX() == null) {
                sb.append("Значения координаты X  не может быть null ").append("\n");
            }
            if (coordinatesDTO.getY() == null) {
                sb.append("Значения координаты Y  не может быть null").append("\n");
            }
        }

        if (creationDate == null) {
            creationDate = Date.from(Instant.now());
        }
        if (birthday==null){
            sb.append("Значение даты рождения не может быть null").append("\n");
        }
        if (colorDTO == null) {
            sb.append("Значение цвета волос не может быть null").append("\n");
        }
        if (locationDTO == null) {
            sb.append("Значение локации не может быть null").append("\n");
        }else {
            if (locationDTO.getY() == null) {
                sb.append("Координата Y локации не может быть равна null").append("\n");
            }
            if (locationDTO.getName() == null) {
                sb.append("Значения имени локации не может быть равна null").append("\n");
            }
        }
        if (!(sb.length()==0)){
            sb.insert(0, "Во входном файле есть некорректные данные. В следующий раз исправьте все виды ошибок: \n");
            throw new InvalidInputException(sb.toString());
        }
    }
}
