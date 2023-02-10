package com.bakheet.service;

import com.bakheet.entiy.Role;

public interface RoleService {

    Role loadRoleByName(String name);

    Role createRole(String name);


}
