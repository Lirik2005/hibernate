package com.lirik.util;

import com.lirik.entity.companies.Company;
import com.lirik.entity.users.Birthday;
import com.lirik.entity.users.Payment;
import com.lirik.entity.users.PersonalInfo;
import com.lirik.entity.users.User;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.time.Month;

@UtilityClass
public class TestDataImporter {

    public void importData(SessionFactory sessionFactory) {
        @Cleanup Session session = sessionFactory.openSession();

        Company microsoft = saveCompany(session, "Microsoft");
        Company apple = saveCompany(session, "Apple");
        Company google = saveCompany(session, "Google");

        User billGates = saveUser(session, "Bill", "Gates",
                                  new Birthday(LocalDate.of(1955, Month.OCTOBER, 28)), microsoft);
        User steveJobs = saveUser(session, "Steve", "Jobs",
                                  new Birthday(LocalDate.of(1955, Month.FEBRUARY, 24)), apple);
        User sergeyBrin = saveUser(session, "Sergey", "Brin",
                                   new Birthday(LocalDate.of(1973, Month.AUGUST, 21)), google);
        User timCook = saveUser(session, "Tim", "Cook",
                                new Birthday(LocalDate.of(1960, Month.NOVEMBER, 1)), apple);
        User dianeGreene = saveUser(session, "Diane", "Greene",
                                    new Birthday(LocalDate.of(1955, Month.JANUARY, 1)), google);

        savePayment(session, billGates, 100);
        savePayment(session, billGates, 300);
        savePayment(session, billGates, 500);

        savePayment(session, steveJobs, 250);
        savePayment(session, steveJobs, 600);
        savePayment(session, steveJobs, 500);

        savePayment(session, timCook, 400);
        savePayment(session, timCook, 300);

        savePayment(session, sergeyBrin, 500);
        savePayment(session, sergeyBrin, 500);
        savePayment(session, sergeyBrin, 500);

        savePayment(session, dianeGreene, 300);
        savePayment(session, dianeGreene, 300);
        savePayment(session, dianeGreene, 300);
    }

    private Company saveCompany(Session session, String name) {
        Company company = Company.builder()
                                 .name(name)
                                 .build();
        session.persist(company);

        return company;
    }

    private User saveUser(Session session,
                          String firstName,
                          String lastName,
                          Birthday birthday,
                          Company company) {
        User user = User.builder()
                        .userName(firstName + lastName)
                        .personalInfo(PersonalInfo.builder()
                                                  .firstName(firstName)
                                                  .lastName(lastName)
                                                  .birthDate(birthday)
                                                  .build())
                        .company(company)
                        .build();
        session.save(user);

        return user;
    }

    private void savePayment(Session session, User user, Integer amount) {
        Payment payment = Payment.builder()
                                 .receiver(user)
                                 .amount(amount)
                                 .build();
        session.save(payment);
    }
}
