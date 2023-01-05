package com.lirik.entity.users;

import com.lirik.entity.BaseEntity;
import com.lirik.entity.chat.UserChat;
import com.lirik.entity.companies.Company;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "userName")
@ToString(exclude = {"company", "profile", "userChats", "payments"})
@AllArgsConstructor
@Builder

/**
 * Аннотация @Entity необходима для того, чтобы указать, что данный POJO-класс является сущностью Hibernate
 * Т. к. название класса (User) отличается от названия таблицы (users), необходимо добавить @Table и указать название таблицы для данной
 * Entity
 *
 * Также данный класс должен быть указан в файле hibernate.cfg.xml или в классе HibernateUtil
 */

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // Так указывается стратегия наследования
@Entity
@Table(name = "users")
public class User implements Comparable<User>, BaseEntity<Long> {

    /**
     * Каждая сущность в Hibernate должна иметь первичный ключ. Для этого над полем, которое будет первичным ключом проставляется
     * аннотация @Id
     * Если название колонок в таблице отличается от названия полей (присутствует CamelCase или Snake_case), то необходимо указать
     * маппинг на колонки с помощью аннотации @Column и в ней указать название колонки как в таблице
     * Аннотация @Enumerated нужна для работы с Enum. Если указать @Enumerated(EnumType.STRING), то из Enum мы будем получать
     * соответствующее имя. Если указать @Enumerated(EnumType.ORDINAL), то будем получать порядковый номер поля из Enum и этот параметр
     * установлен ПО УМОЛЧАНИЮ!!!
     */

    @Id

    /**
     * Аннотация @GeneratedValue нужна для генерации id и чтобы сказать hibernate, чтобы он не вставлял поле id в базу данных иначе он
     * будет требовать, чтобы id был не null.
     * Есть несколько стратегий генерации id:
     * - GenerationType.AUTO - использует IDENTITY, SEQUENCE или TABLE. Зависит от СУБД и диалекта. У Postgres SQL это IDENTITY
     * - GenerationType.IDENTITY - используется практически всегда и имеет максимальный приоритет перед всеми остальными стратегиями
     * - GenerationType.SEQUENCE - аналог счетчика, который увеличивается на 1 при каждом вызове
     * - GenerationType.TABLE - жуткое legacy, которое использовали до создания IDENTITY и SEQUENCE
     */
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @Column(name = "username", unique = true)
    private String userName;

    /**
     * Задокументированный код (поля и их аннотации) переехали в новы класс PersonalInfo для дальнейшей настройки юзера!!!
     */

//    @Column(name = "firstname")
//    private String firstName;
//    @Column(name = "lastname")
//    private String lastName;
//
//    @Convert(converter = BirthdayConverter.class) // С этой аннотацией Hibernate знает как использовать класс Birthday
//    @Column(name = "birth_date")
//    private Birthday birthDate;  // В таком виде SQL не знает как работать с этим полем и нам нужен кастомный конвертер!!!

    /**
     * Аннотация @AttributeOverride позволяет связать наш встраиваемый компонент с нашей сущностью и установить соответствие названий полей
     * сущности с названиями колонок в таблице
     */

    @AttributeOverride(name = "firstName", column = @Column(name = "firstname"))
    @AttributeOverride(name = "lastName", column = @Column(name = "lastname"))
    @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
    private PersonalInfo personalInfo;

    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Аннотация ниже позволяет создать свой собственный кастомный тип. В данном случае String будет приводиться к формату JSON, однако
     * для корректной работы необходима зависимость для парсинга JSON (например библиотека Jackson)
     */

    @JdbcTypeCode(SqlTypes.JSON)
    private String info;

    /**
     * Аннотация @ManyToOne (Many относится к users, а One относится к Company) нужна для маппинга вида: много юзеров к одной компании.
     * Параметр optional = false позволяет нам сделать inner join. Параметр fetch = FetchType.LAZY это ленивая инициализация и
     * используется для того, чтобы инициализировать сущность Company, тогда, когда мы попросим ее поля (т.е. используется Hibernate proxy)
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id") // Эта аннотация используется, чтобы указать по какой колонке идет маппинг
    private Company company;

//    @OneToOne(mappedBy = "userForMapping", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Profile profile;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private Set<UserChat> userChats = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "receiver")
    private List<Payment> payments = new ArrayList<>();


    @Override
    public int compareTo(User o) {
        return userName.compareTo(o.userName);
    }

    public String fullName() {
        return getPersonalInfo().getFirstName() + " " + getPersonalInfo().getLastName();
    }
}
