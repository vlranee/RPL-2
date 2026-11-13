/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.signup.dao;

import com.mycompany.signup.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(User user) {
        em.persist(user);
    }

    @Override
    public User update(User user) {
        return em.merge(user);
    }

    @Override
    public void delete(Integer id) {
        User u = em.find(User.class, id);
        if (u != null) {
            em.remove(u);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return em.createQuery("select u from User u where u.username = :un", User.class)
                .setParameter("un", username)
                .getResultStream()
                .findFirst();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        Long count = em.createQuery(
                "select count(u) from User u where u.username = :un", Long.class)
                .setParameter("un", username)
                .getSingleResult();
        return count > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return em.createQuery("select u from User u order by u.id", User.class)
                .getResultList();
    }
}