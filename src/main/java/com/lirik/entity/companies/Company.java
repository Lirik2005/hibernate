package com.lirik.entity.companies;

import com.lirik.entity.users.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Также данный класс должен быть указан в файле hibernate.cfg.xml или в классе HibernateUtil
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    /**
     * Аннотация @OneToMany (One относится к Company, а Many относится к users) нужна для маппинга вида: много юзеров к одной компании.
     * Там где Many у нас всегда должна быть коллекция.
     * Если в классе User убрать @ManyToOne и @JoinColumn над полем private Company company, то тогда в классе Company необходимо указать
     * @JoinColumn(name = "company_id"). В противном случае нам достаточно аннотации @OneToMany с параметром mappedBy = "company", т.е.
     * указать на поле в классе User
     */

    @OneToMany(mappedBy = "company")
//    @JoinColumn(name = "company_id")
    private List<User> users;
}
