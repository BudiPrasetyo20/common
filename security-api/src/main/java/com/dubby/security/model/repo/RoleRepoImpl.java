package com.dubby.security.model.repo;


import com.dubby.common.model.abstracts.ACommonPickerRepo;
import com.dubby.security.model.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepoImpl extends ACommonPickerRepo<Role> implements RoleRepo {

    @Override
    protected Class<Role> getEntityClass() {
        return Role.class;
    }

}
