package com.dubby.security.model.repo;


import com.dubby.base.model.repo.CacheContainerRepo;
import com.dubby.base.model.repo.PickerRepo;
import com.dubby.base.model.repo.Repo;
import com.dubby.common.model.entity.PickerItem;
import com.dubby.security.model.entity.Role;

import java.util.List;

public interface RoleRepo extends Repo<Role>, PickerRepo<Role>, CacheContainerRepo<Role> {
    public List<PickerItem> getListUserByRole(String idRole) throws Exception;
}
