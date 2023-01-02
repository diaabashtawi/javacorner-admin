package com.javacorner.admin.service;

import com.javacorner.admin.entiy.User;

public interface UserService {

    User loadUserByEmail(String email);

    User createUser(String email, String password);

    void assignRoleToUser(String email, String roleName);


}
