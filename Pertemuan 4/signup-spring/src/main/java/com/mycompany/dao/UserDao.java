/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.signup.dao;

import com.mycompany.signup.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    void save(User user);
    User update(User user);
    void delete(Integer id);
    Optional<User> findById(Integer id);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    List<User> findAll();
}