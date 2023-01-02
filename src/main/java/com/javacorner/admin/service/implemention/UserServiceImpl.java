package com.javacorner.admin.service.implemention;

import com.javacorner.admin.dao.RoleDao;
import com.javacorner.admin.dao.UserDao;
import com.javacorner.admin.entiy.Role;
import com.javacorner.admin.entiy.User;
import com.javacorner.admin.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    private RoleDao roleDao;

    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public User loadUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User createUser(String email, String password) {
        return userDao.save(new User(email, password));
    }

    @Override
    public void assignRoleToUser(String email, String roleName) {
        User user =
                loadUserByEmail(email);
        Role role =
                roleDao.findByName(roleName);
        user.assignRoleToUser(role);
    }
}
