package com.dubby.security.model.repo;


import com.dubby.base.enumeration.StatusType;
import com.dubby.base.exception.BaseException;
import com.dubby.base.model.NullEmptyChecker;
import com.dubby.common.model.ValueTextBuilder;
import com.dubby.common.model.abstracts.ACommonPickerRepo;
import com.dubby.common.model.entity.PickerItem;
import com.dubby.security.model.entity.Role;
import com.dubby.security.model.entity.User;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoleRepoImpl extends ACommonPickerRepo<Role> implements RoleRepo {

    @Override
    protected Class<Role> getEntityClass() {
        return Role.class;
    }

    @Override
    public List<PickerItem> getListUserByRole(String idRole) throws Exception {

        Map<String, String> userMap = new HashMap<>();

        Role role = find(Long.valueOf(idRole));

        if (NullEmptyChecker.isNotNullOrEmpty(role)) {

            Hibernate.initialize(role.getUsers());

            for (User user : role.getUsers()) {
                userMap.put(user.getId(), user.getName());
            }
        }


        return ValueTextBuilder.buildFromMap(userMap);
    }
}
