package com.itmo.programming.utils;

import com.itmo.programming.encryption.SHA512Generator;
import com.itmo.programming.repository.PersonRepository;
import com.itmo.programming.repository.UserRepository;
import com.itmo.programming.service.PersonService;
import com.itmo.programming.service.UserService;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class ServiceManager {
    private UserService userService;
    private PersonService personService;

    public ServiceManager() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        initAllServices();
    }

    private void initAllServices() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class}, (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor(sessionFactory);
        UserRepository userRepository = new UserRepository(session);
        userService = new ByteBuddy()
                .subclass(UserService.class)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(transactionInterceptor))
                .make()
                .load(UserService.class.getClassLoader())
                .getLoaded()
                .getDeclaredConstructor(SHA512Generator.class, UserRepository.class)
                .newInstance( new SHA512Generator(), userRepository);

        PersonRepository personRepository = new PersonRepository(session);
        personService = new ByteBuddy()
                .subclass(PersonService.class)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(transactionInterceptor))
                .make()
                .load(PersonService.class.getClassLoader())
                .getLoaded()
                .getDeclaredConstructor(PersonRepository.class)
                .newInstance(personRepository);
    }

    public UserService getUserService() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return userService;
    }

    public PersonService getPersonService() {
        return personService;
    }
}
