package com.itmo.programming.repository;

import com.itmo.programming.model.User;
import org.hibernate.Session;

import java.util.List;


/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class UserRepository extends RepositoryBase<Long, User> {
    public UserRepository(Session session) {
        super(User.class, session);
    }

    public List<User> findByLogin(String login) {
        return getSession().createQuery("select user from User user where user.login =:login", User.class)
                .setParameter("login", login).list();
    }

    public List<User> findByLoginAndPassword(String login, String password) {
        return getSession().createQuery("select user from User user where user.login =:login and user.password =:password", User.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .list();
    }
}
