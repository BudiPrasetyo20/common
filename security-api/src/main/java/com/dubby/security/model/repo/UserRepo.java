package com.dubby.security.model.repo;


import com.dubby.base.exception.BaseException;
import com.dubby.base.model.PagedResult;
import com.dubby.base.model.repo.CacheContainerRepo;
import com.dubby.base.model.repo.PickerRepo;
import com.dubby.base.model.repo.Repo;
import com.dubby.security.model.entity.User;
import org.hibernate.criterion.Criterion;

import java.util.List;

public interface UserRepo extends Repo<User>, PickerRepo<User>, CacheContainerRepo<User> {

    public void changePassword(String user, String oldPassword, String newPassword, String updateBy)
            throws BaseException;
    public List<User> findAndLoadRoles();
    public User findAndLoadRoles(String id);
    public PagedResult findPagedAndLoadRoles(Criterion criterion, Integer pageSize, Integer pageIndex) throws Exception;

    public void retrieveRoles(User user);
}
