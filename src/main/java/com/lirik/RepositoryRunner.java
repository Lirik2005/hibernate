package com.lirik;

import com.lirik.dao.CompanyRepository;
import com.lirik.dao.PaymentRepository;
import com.lirik.dao.UserRepository;
import com.lirik.dto.UserCreateDto;
import com.lirik.entity.users.Birthday;
import com.lirik.entity.users.PersonalInfo;
import com.lirik.entity.users.Role;
import com.lirik.interceptor.TransactionInterceptor;
import com.lirik.mapper.CompanyReadMapper;
import com.lirik.mapper.UserCreateMapper;
import com.lirik.mapper.UserReadMapper;
import com.lirik.service.UserService;
import com.lirik.util.HibernateUtil;
import jakarta.transaction.Transactional;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.time.LocalDate;

@Transactional
public class RepositoryRunner {

    public static void main(String[] args)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                                                               (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(),
                                                                                                       args1));
//            session.beginTransaction();
            UserRepository userRepository = new UserRepository(session);
            CompanyRepository companyRepository = new CompanyRepository(session);
            UserReadMapper userReadMapper = new UserReadMapper(new CompanyReadMapper());
            UserCreateMapper userCreateMapper = new UserCreateMapper(companyRepository);
            PaymentRepository paymentRepository = new PaymentRepository(session);

            TransactionInterceptor transactionInterceptor = new TransactionInterceptor(sessionFactory);

            UserService userService = new ByteBuddy()
                    .subclass(UserService.class)
                    .method(ElementMatchers.any())
                    .intercept(MethodDelegation.to(transactionInterceptor))
                    .make()
                    .load(UserService.class.getClassLoader())
                    .getLoaded()
                    .getDeclaredConstructor(UserRepository.class, UserReadMapper.class, UserCreateMapper.class)
                    .newInstance(userRepository, userReadMapper, userCreateMapper);

//            UserService userService = new UserService(userRepository, userReadMapper, userCreateMapper);

            userService.findById(1L).ifPresent(System.out::println);

            UserCreateDto userCreateDto = new UserCreateDto(
                    PersonalInfo.builder()
                                .firstName("Liza")
                                .lastName("Lizova")
                      //          .birthDate(new Birthday(LocalDate.now()))
                                .build(),
                    "liza3@gmail.com",
                    null,
                    Role.USER,
                    1
            );
            userService.create(userCreateDto);

//            session.getTransaction().commit();

        }
    }

}
