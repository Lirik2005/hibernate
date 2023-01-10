package com.lirik.mapper;

import com.lirik.dao.CompanyRepository;
import com.lirik.dto.UserCreateDto;
import com.lirik.entity.users.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, User> {

    private final CompanyRepository companyRepository;

    @Override
    public User mapFrom(UserCreateDto object) {
        return User.builder()
                   .personalInfo(object.personalInfo())
                   .userName(object.userName())
                   .info(object.info())
                   .role(object.role())
                   .company(companyRepository.findById(object.companyId()).orElseThrow(IllegalArgumentException::new))
                   .build();
    }
}
