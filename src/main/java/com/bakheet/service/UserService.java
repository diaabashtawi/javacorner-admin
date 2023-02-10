package com.bakheet.service;

import com.bakheet.entiy.User;

public interface UserService {

    User loadUserByEmail(String email);

    User createUser(String email, String password);

    void assignRoleToUser(String email, String roleName);


}
