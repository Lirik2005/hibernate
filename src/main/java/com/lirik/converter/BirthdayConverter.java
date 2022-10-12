package com.lirik.converter;

import com.lirik.entity.Birthday;
import jakarta.persistence.AttributeConverter;

import java.sql.Date;
import java.util.Optional;

/**
 * Конвертер реализует интерфейс AttributeConverter, в котором указываем первым параметром класс, который надо конвертировать, а вторым
 * параметром указываем класс в который необходимо конвертировать (см. импорты)
 */

public class BirthdayConverter implements AttributeConverter<Birthday, Date> {

    @Override
    public Date convertToDatabaseColumn(Birthday attribute) {
        return Optional.ofNullable(attribute)
                       .map(birthday -> birthday.getBirthDate())
                       .map(date -> Date.valueOf(date))
                       .orElse(null);
    }

    @Override
    public Birthday convertToEntityAttribute(Date dbData) {
        return Optional.ofNullable(dbData)
                       .map(date -> date.toLocalDate())
                       .map(birthday -> new Birthday(birthday))
                       .orElse(null);
    }
}
