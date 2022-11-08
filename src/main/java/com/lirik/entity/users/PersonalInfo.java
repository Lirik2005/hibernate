package com.lirik.entity.users;

import com.lirik.converter.BirthdayConverter;
import com.lirik.entity.users.Birthday;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable   // Это аннотация используется, чтобы определить класс как ВСТРАЕВАЕМЫЙ КОМПОНЕТ!!!
public class PersonalInfo {

    /**
     * В этом классе нам не требуется аннотация @Column над каждым полем. Чтобы установить соответствие наших полей с колонками таблицы
     * мы используем другую аннотацию, но уже в классе юзер над полем класса PersonalInfo
     */

    private String firstName;
    private String lastName;
    @Convert(converter = BirthdayConverter.class) // С этой аннотацией Hibernate знает как использовать класс Birthday
    private Birthday birthDate;  // В таком виде SQL не знает как работать с этим полем и нам нужен кастомный конвертер!!!
}
