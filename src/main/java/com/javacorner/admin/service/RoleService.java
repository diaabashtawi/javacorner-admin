package com.javacorner.admin.service;

import com.javacorner.admin.entiy.Role;

public interface RoleService {

    Role loadRoleByName(String name);

    Role createRole(String name);


}
