package com.lirik.dto;

import com.lirik.entity.users.PersonalInfo;
import com.lirik.entity.users.Role;

public record UserReadDto(Long id,
                          PersonalInfo personalInfo,
                          String userName,
                          String info,
                          Role role,
                          CompanyReadDto company) {

}
