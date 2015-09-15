package com.raibledesigns.dao.jpa;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.raibledesigns.dao.LookupDao;
import com.raibledesigns.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * JPA implementation of LookupDao.
 *
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a>
 */
@Repository
public class LookupDaoJpa implements LookupDao {
    private Log log = LogFactory.getLog(LookupDaoJpa.class);
    @PersistenceContext
    EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Role> getRoles() {
        log.debug("Retrieving all role names...");

        return entityManager.createQuery(
                "select r from Role r order by name").getResultList();
    }
}
