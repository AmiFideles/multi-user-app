package com.itmo.programming.repository;

import com.itmo.programming.model.Person;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class PersonRepository extends RepositoryBase<Long, Person> {
    public PersonRepository(Session session) {
        super(Person.class, session);
    }

    public List<Person> findPersonByUserId(long userId) {
        return getSession().createQuery("select person from Person person where person.user.id =:userId", Person.class)
                .setParameter("userId", userId)
                .list();
    }

    public Optional<Person> findByKey(Long key) {
        return Optional.ofNullable(getSession().createQuery("select person from Person person where person.key =:key", Person.class)
                .setParameter("key", key)
                .getSingleResult());

    }

    public List<Person> findByBirthday(LocalDate date) {
        return getSession().createQuery("select person from Person person where person.birthday =:date", Person.class)
                .setParameter("date", date)
                .list();
    }

    public List<Person> findByBirthdayAndUserId(LocalDate date, long userId) {
        return getSession().createQuery("select person from Person person where person.birthday =:date and person.user.id =:userId", Person.class)
                .setParameter("date", date)
                .setParameter("userId", userId)
                .list();
    }

    public List<Person> findByGreaterKeyAndUserId(long key, long userId) {
        return getSession().createQuery("select person from Person person where person.key > :key and person.user.id =:userId", Person.class)
                .setParameter("key", key)
                .setParameter("userId", userId)
                .list();
    }

}
