package com.lirik.dao;

import com.lirik.entity.users.Payment;
import com.lirik.entity.users.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    /**
     * Возвращает всех сотрудников
     */
    public List<User> findAll(Session session) {
        return session.createQuery("select u from User u", User.class).list(); // вместо u можно написать любую букву
    }

    /**
     * Возвращает всех сотрудников с указанным именем
     */
    public List<User> findAllByFirstName(Session session, String firstName) {
        Query<User> query = session.createQuery("select u from User u where u.personalInfo.firstName = :firstname", User.class);
        return query.setParameter("firstname", firstName).list();
    }

    /**
     * Возвращает первые {limit} сотрудников, упорядоченных по дате рождения (в порядке возрастания)
     */
    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit) {
        return session.createQuery("select u from User u order by u.personalInfo.birthDate", User.class)
                      .setMaxResults(limit)
                      .list();
    }

    /**
     * Возвращает всех сотрудников компании с указанным названием
     */
    public List<User> findAllByCompanyName(Session session, String companyName) {

        /**
         * Идем через таблицу User
         */

//        return session.createQuery("select u from User u where u.company = :company", User.class)
//                      .setParameter("company", companyName)
//                      .list();

        /**
         * Идем через таблицу Company
         */

        return session.createQuery("select u  from Company c join c.users u where c.name= :company", User.class)
                      .setParameter("company", companyName)
                      .list();
    }

    /**
     * Возвращает все выплаты, полученные сотрудниками компании с указанными именем,
     * упорядоченные по имени сотрудника, а затем по размеру выплаты
     */
    public List<Payment> findAllPaymentsByCompanyName(Session session, String companyName) {
        return session.createQuery("select p from Payment p join p.receiver u join u.company c where c.name = :company order by u" +
                                           ".personalInfo.firstName, p.amount", Payment.class)
                      .setParameter("company", companyName)
                      .list();
    }

    /**
     * Возвращает среднюю зарплату сотрудника с указанными именем и фамилией
     */
    public Double findAveragePaymentAmountByFirstAndLastNames(Session session, String firstName, String lastName) {
        return session.createQuery("select avg (p.amount) from Payment p join p.receiver u where u.personalInfo.firstName = :firstname " +
                                           "and u.personalInfo.lastName = :lastname", Double.class)
                      .setParameter("firstname", firstName)
                      .setParameter("lastname", lastName)
                      .uniqueResult();
    }

    /**
     * Возвращает для каждой компании: название, среднюю зарплату всех её сотрудников. Компании упорядочены по названию.
     */
    public List<Object[]> findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(Session session) {
        return session.createQuery("select c.name, avg (p.amount) from Company c join c.users u join u.payments p group by c.name order " +
                                           "by c.name",
                                   Object[].class)
                      .list();
    }

    /**
     * Возвращает список: сотрудник (объект User), средний размер выплат, но только для тех сотрудников, чей средний размер выплат
     * больше среднего размера выплат всех сотрудников.
     * Упорядочить по имени сотрудника
     */
    public List<Object[]> isItPossible(Session session) {
        return session.createQuery("select u, avg(p.amount) from User u " +
                                           "join u.payments p " +
                                           "group by u " +
                                           "having avg(p.amount) > (select avg(p.amount) from Payment p) " +
                                           "order by u.personalInfo.firstName", Object[].class)
                      .list();
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
