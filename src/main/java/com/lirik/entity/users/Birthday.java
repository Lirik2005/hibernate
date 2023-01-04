package com.lirik.entity.users;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Birthday implements Comparable<Birthday> {

    private LocalDate birthDate;

    public long getAge() {
        return ChronoUnit.YEARS.between(birthDate, LocalDate.now());
    }


    @Override
    public int compareTo(Birthday o) {
        return birthDate.compareTo(o.birthDate);
    }
}
