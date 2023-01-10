package com.lirik.mapper;

import com.lirik.dto.CompanyReadDto;
import com.lirik.entity.companies.Company;
import org.hibernate.Hibernate;

public class CompanyReadMapper implements Mapper<Company, CompanyReadDto> {

    @Override
    public CompanyReadDto mapFrom(Company object) {
        return new CompanyReadDto(
                object.getId(),
                object.getName()
        );
    }
}
