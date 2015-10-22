package com.dubby.security.model.repo;

import com.dubby.base.model.repo.ARepo;
import com.dubby.security.model.entity.Rights;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RightsRepoImpl extends ARepo<Rights> implements RightsRepo {

    @Override
    protected Class<Rights> getEntityClass() {
        return Rights.class;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Rights> findRightsTree() throws Exception {

        DetachedCriteria firstLevelCriteria = DetachedCriteria.forClass(Rights.class)
                .add(Restrictions.isNull("parent"))
                .addOrder(Order.asc("id"));

        List<Rights> firstLevelRights = find(firstLevelCriteria);
        for (Rights firstRight : firstLevelRights) {
            firstRight.getListOfChild().addAll(findChildRights(firstRight.getId()));
            for (Rights secondRight : firstRight.getListOfChild()) {
                secondRight.getListOfChild().addAll(findChildRights(secondRight.getId()));
            }
        }

        return firstLevelRights;
    }

    @Transactional(readOnly = true)
    public List<Rights> findChildRights(String parentId) {
        DetachedCriteria secondLevelCriteria = DetachedCriteria.forClass(Rights.class, "r")
                .createAlias("parent", "p")
                .add(Restrictions.eq("p.id", parentId))
                .addOrder(Order.asc("r.id"));
        return find(secondLevelCriteria);
    }
}
