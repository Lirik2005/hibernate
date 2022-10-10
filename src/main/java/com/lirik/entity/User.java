package com.lirik.entity;

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
     */

    @Id
    @Column(name = "username")
    private String userName;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "age")
    private Integer age;
}
