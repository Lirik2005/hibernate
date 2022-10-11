package com.lirik.entity;

import com.lirik.converter.BirthdayConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

/**
 * Аннотация @Entity необходима для того, чтобы указать, что данный POJO-класс является сущностью Hibernate
 * Т. к. название класса (User) отличается от названия таблицы (users), необходимо добавить @Table и указать название таблицы для данной
 * Entity
 */

@Entity
@Table(name = "users")
public class User {

    /**
     * Каждая сущность в Hibernate должна иметь первичный ключ. Для этого над полем, которое будет первичным ключом проставляется
     * аннотация @Id
     * Если название колонок в таблице отличается от названия полей (присутствует CamelCase или Snake_case), то необходимо указать
     * маппинг на колонки с помощью аннотации @Column и в ней указать название колонки как в таблице
     * Аннотация @Enumerated нужна для работы с Enum. Если указать @Enumerated(EnumType.STRING), то из Enum мы будем получать
     * соответствующее имя. Если указать @Enumerated(EnumType.ORDINAL), то будем получать порядковый номер поля из Enum и этот параметр
     * установлен ПО УМОЛЧАНИЮ!!!
     *
     */

    @Id
    @Column(name = "username")
    private String userName;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;

    @Convert(converter = BirthdayConverter.class) // С этой аннотацией Hibernate знает как использовать класс Birthday
    @Column(name = "birth_date")
    private Birthday birthDate;  // В таком виде SQL не знает как работать с этим полем и нам нужен кастомный конвертер!!!
    @Enumerated(EnumType.STRING)
    private Role role;
}
