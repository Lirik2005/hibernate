package com.lirik.mapping;

import com.lirik.entity.chat.UserChat;
import com.lirik.entity.companies.Company;
import com.lirik.entity.users.PersonalInfo;
import com.lirik.entity.users.Profile;
import com.lirik.entity.users.Role;
import com.lirik.entity.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
public class Programmer extends User {

//    @Enumerated(EnumType.STRING)
//    private Language language;


//    public Programmer(Long id, String userName, PersonalInfo personalInfo, Role role, String info,
//                      Company company, Profile profile,
//                      Set<UserChat> userChats, Language language) {
//        super(id, userName, personalInfo, role, info, company, profile, userChats);
//        this.language = language;
//    }
}
