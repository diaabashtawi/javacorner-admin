package com.javacorner.admin.service.implementation;

import com.javacorner.admin.dao.RoleDao;
import com.javacorner.admin.entiy.Role;
import com.javacorner.admin.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role loadRoleByName(String name) {
        return roleDao.findByName(name);
    }

    @Override
    public Role createRole(String name) {
        return roleDao.save(new Role(name));
    }
}
