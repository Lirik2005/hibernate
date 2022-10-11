package com.lirik;

import com.lirik.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

class HibernateRunnerTest {

    @SneakyThrows
    @Test
    void checkReflectionApi() {
        User user = User.builder()
                        .userName("ivan@gmail.com")
                        .firstName("Ivan")
                        .lastName("Ivanov")
                        .birthDate(LocalDate.of(2000, 1, 19))
                        .age(22)
                        .build();

        String sql = """
                insert
                into
                %s
                (%s)
                values
                (%s)
                """;

        String tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
                                   .map(tableAnnotation -> tableAnnotation.name())
                                   .orElse(user.getClass().getName());

        Field[] declaredFields = user.getClass().getDeclaredFields();
        String columnNames = Arrays.stream(declaredFields)
                                   .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
                                                         .map(column -> column.name())
                                                         .orElse(field.getName()))
                                   .collect(Collectors.joining(", "));

        String columnValues = Arrays.stream(declaredFields)
                                    .map(field -> "?")
                                    .collect(Collectors.joining(", "));

        System.out.println(sql.formatted(tableName, columnNames, columnValues));

        Connection connection = null;
        PreparedStatement statement = connection.prepareStatement(sql.formatted(tableName, columnNames, columnValues));

        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            statement.setObject(1, declaredField.get(user));
        }
    }
}