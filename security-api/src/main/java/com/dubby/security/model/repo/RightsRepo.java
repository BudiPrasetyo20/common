package com.dubby.security.model.repo;

import com.dubby.base.model.repo.Repo;
import com.dubby.security.model.entity.Rights;

import java.util.List;

public interface RightsRepo extends Repo<Rights> {
    List<Rights> findRightsTree() throws Exception;

}
