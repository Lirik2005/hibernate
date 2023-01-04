package com.lirik.dao;

import com.lirik.dto.CompanyDto;
import com.lirik.entity.companies.Company;
import com.lirik.entity.companies.Company_;
import com.lirik.entity.users.Birthday;
import com.lirik.entity.users.Payment;
import com.lirik.entity.users.Payment_;
import com.lirik.entity.users.PersonalInfo_;
import com.lirik.entity.users.User;
import com.lirik.entity.users.User_;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.ListJoin;
import jakarta.persistence.criteria.MapJoin;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import org.hibernate.query.criteria.JpaSubQuery;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDaoCriteriaApi {

    private static final UserDaoCriteriaApi INSTANCE = new UserDaoCriteriaApi();

    /**
     * Возвращает всех сотрудников
     */
    public List<User> findAll(Session session) {
        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();

        JpaCriteriaQuery<User> criteria = cb.createQuery(User.class);
        JpaRoot<User> user = criteria.from(User.class);

        criteria.select(user);

        return session.createQuery(criteria)
                      .list();
    }

    /**
     * Возвращает всех сотрудников с указанным именем
     */
    public List<User> findAllByFirstName(Session session, String firstName) {
        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();

        JpaCriteriaQuery<User> criteria = cb.createQuery(User.class);
        JpaRoot<User> user = criteria.from(User.class);

        criteria.select(user).where(

                /**
                 * Можно писать через String, как в коде ниже
                 */

//                cb.equal(user.get("personalInfo").get("firstName"), firstName)

                /**
                 * Если подключить jpamodelgen и выполнить таску classes из директории build в Gradle, то можно писать следующий код
                 * В пакете build -> generated -> annotationProcessor мы увидим сгенерированные классы с нижним подчеркиванием
                 */

                cb.equal(user.get(User_.personalInfo).get(PersonalInfo_.firstName), firstName)
        );

        return session.createQuery(criteria)
                      .list();

    }

    /**
     * Возвращает первые {limit} сотрудников, упорядоченных по дате рождения (в порядке возрастания)
     */
    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit) {
        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();

        JpaCriteriaQuery<User> criteria = cb.createQuery(User.class);
        JpaRoot<User> user = criteria.from(User.class);

        criteria.select(user).orderBy(
                cb.asc(user.get(User_.personalInfo).get("birthDate")));

        return session.createQuery(criteria)
                      .setMaxResults(limit)
                      .list();
    }

    /**
     * Возвращает всех сотрудников компании с указанным названием
     */
    public List<User> findAllByCompanyName(Session session, String companyName) {
        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();

        JpaCriteriaQuery<User> criteria = cb.createQuery(User.class);
        JpaRoot<Company> company = criteria.from(Company.class);

        MapJoin<Company, String, User> users = company.join(Company_.users);
        criteria.select(users).where(
                cb.equal(company.get(Company_.name), companyName)
        );

        return session.createQuery(criteria)
                      .list();
    }

    /**
     * Возвращает все выплаты, полученные сотрудниками компании с указанными именем,
     * упорядоченные по имени сотрудника, а затем по размеру выплаты
     */
    public List<Payment> findAllPaymentsByCompanyName(Session session, String companyName) {
        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();

        JpaCriteriaQuery<Payment> criteria = cb.createQuery(Payment.class);
        JpaRoot<Payment> payment = criteria.from(Payment.class);
        Join<Payment, User> user = payment.join(Payment_.receiver);
        Join<User, Company> company = user.join(User_.company);

        criteria.select(payment).where(
                        cb.equal(company.get(Company_.name), companyName)
                )
                .orderBy(
                        cb.asc(user.get(User_.personalInfo).get(PersonalInfo_.firstName)),
                        cb.asc(payment.get(Payment_.amount))
                );

        return session.createQuery(criteria)
                      .list();
    }

    /**
     * Возвращает среднюю зарплату сотрудника с указанными именем и фамилией
     */
    public Double findAveragePaymentAmountByFirstAndLastNames(Session session, String firstName, String lastName) {
        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
        JpaCriteriaQuery<Double> criteria = cb.createQuery(Double.class);

        JpaRoot<Payment> payment = criteria.from(Payment.class);
        Join<Payment, User> user = payment.join(Payment_.receiver);

        List<Predicate> predicates = new ArrayList<>();
        if (firstName != null) {
            predicates.add(cb.equal(user.get(User_.personalInfo).get(PersonalInfo_.firstName), firstName));
        }
        if (firstName != null) {
            predicates.add(cb.equal(user.get(User_.personalInfo).get(PersonalInfo_.lastName), lastName));
        }

        criteria.select(cb.avg(payment.get(Payment_.amount))).where(
                predicates.toArray(Predicate[]::new)

        );

        return session.createQuery(criteria)
                      .uniqueResult();

    }

    /**
     * Возвращает для каждой компании: название, среднюю зарплату всех её сотрудников. Компании упорядочены по названию.
     */
    public List<CompanyDto> findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(Session session) {
        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
        JpaCriteriaQuery<CompanyDto> criteria = cb.createQuery(CompanyDto.class);
        JpaRoot<Company> company = criteria.from(Company.class);
        MapJoin<Company, String, User> user = company.join(Company_.users, JoinType.INNER);
        ListJoin<User, Payment> payment = user.join(User_.payments);

//        criteria.select(
//                        cb.construct(CompanyDto.class,
//                                     company.get(Company_.name),
//                                     cb.avg(payment.get(Payment_.amount)))
//                )
//                .groupBy(company.get(Company_.name))
//                .orderBy(cb.asc(company.get(Company_.name)));

        return session.createQuery(criteria)
                      .list();
    }

    /**
     * Возвращает список: сотрудник (объект User), средний размер выплат, но только для тех сотрудников, чей средний размер выплат
     * больше среднего размера выплат всех сотрудников.
     * Упорядочить по имени сотрудника
     */
    public List<Tuple> isItPossible(Session session) {
        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
        JpaCriteriaQuery<Tuple> criteria = cb.createQuery(Tuple.class);
        JpaRoot<User> user = criteria.from(User.class);
        ListJoin<User, Payment> payment = user.join(User_.payments);

        JpaSubQuery<Double> subQuery = criteria.subquery(Double.class);
        JpaRoot<Payment> paymentSubQuery = subQuery.from(Payment.class);

//        criteria.select(
//                        cb.tuple(
//                                user,
//                                cb.avg(payment.get(Payment_.amount))
//                        )
//                )
//                .groupBy(user.get(User_.id))
//                .having(cb.gt(
//                        cb.avg(payment.get(Payment_.amount)),
//                        subQuery.select(cb.avg(paymentSubQuery.get(Payment_.amount)))
//                ))
//                .orderBy(cb.asc(user.get(User_.personalInfo).get(PersonalInfo_.firstName)));

        return session.createQuery(criteria)
                      .list();
    }

    public static UserDaoCriteriaApi getInstance() {
        return INSTANCE;
    }
}
