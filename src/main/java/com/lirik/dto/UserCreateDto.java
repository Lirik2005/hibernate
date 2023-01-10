package com.lirik.dto;

import com.lirik.entity.users.PersonalInfo;
import com.lirik.entity.users.Role;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public record UserCreateDto (
        @Valid
        PersonalInfo personalInfo,
        @NotNull
        String userName,
        String info,
        Role role,
        Integer companyId) {

}
