package com.lirik.entity.companies;

import com.lirik.entity.BaseEntity;
import com.lirik.entity.users.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SortComparator;
import org.hibernate.annotations.SortNatural;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Также данный класс должен быть указан в файле hibernate.cfg.xml или в классе HibernateUtil
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "name")
@Entity
@Audited
public class Company implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    /**
     * Аннотация @OneToMany (One относится к Company, а Many относится к users) нужна для маппинга вида: много юзеров к одной компании.
     * Там где Many у нас всегда должна быть коллекция.
     * Если в классе User убрать @ManyToOne и @JoinColumn над полем private Company company, то тогда в классе Company необходимо указать
     *
     * @JoinColumn(name = "company_id"). В противном случае нам достаточно аннотации @OneToMany с параметром mappedBy = "company", т.е.
     * указать на поле в классе User
     * cascade = CascadeType.ALL позволит нам при удалении компании удалить и всех ее юзеров. Или создавая компанию мы можем сразу пихать
     * в нее юзеров, которые также будут создаваться и записываться в таблицу
     */
    @Builder.Default
    @NotAudited
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "company_id")
//    @OrderBy("userName desc,  personalInfo.lastName asc")   //данная аннотация используется для сортировки
//    @MapKey(name = "userName") //  Необходима, чтобы указать, какое поле будет ключом
//    @SortNatural // сортирует мапу по ключу
    private Map<String, User> users = new TreeMap<>();

    @Builder.Default
    @NotAudited
    @ElementCollection // Необходима для эмбедбл компонентов
    @CollectionTable(name = "company_locale")
    private Map<String, String> locales = new HashMap<>();

    public void addUser(User user) {
        users.put(user.getUserName(), user);
        user.setCompany(this);
    }
}
