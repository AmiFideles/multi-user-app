package com.itmo.programming.model;

import lombok.*;

import javax.persistence.*;

/**
 * @author Iskandarov Shakhzodbek P3133
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Person implements BaseEntity<Long>, Comparable<Person> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long height; //Поле может быть null
    private java.time.LocalDate birthday; //Поле не может быть null
    private String passportID; //Поле может быть null
    private long key;
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private Color color;

    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinate_id")
    private Coordinates coordinates; //Поле не может быть null
    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;


    @Override
    public int compareTo(Person o) {
        return this.birthday.compareTo(o.birthday);
    }

    @Override
    public String toString() {
        return          "\n    Ключ элемента: " + key +
                        "\nid элемента: " + id +
                        "\nимя человека: " + name +
                        "\n" + coordinates +
                        "\nдата создания  объекта: " + creationDate +
                        "\nрост человека: " + height +
                        "\nдата рождения: " + birthday +
                        "\nid паспорта: '" + passportID +
                        "\nцвет волос: " + color.getColor() +
                        "\n" + location;
    }
}
