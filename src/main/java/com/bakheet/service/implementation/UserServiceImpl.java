package com.bakheet.service.implementation;

import com.bakheet.dao.RoleDao;
import com.bakheet.dao.UserDao;
import com.bakheet.entiy.Role;
import com.bakheet.entiy.User;
import com.bakheet.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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
