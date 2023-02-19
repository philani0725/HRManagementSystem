package HrManagementSys.persistence.collectionbased;

/*
 ** DO NOT CHANGE!!
 */


import HrManagementSys.model.Admin;
import HrManagementSys.persistence.AdminDAO;

import java.util.Optional;

/**
 * {@inheritDoc}
 */
public class AdminDAOImpl extends CollectionBasedDAO<Admin> implements AdminDAO {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Admin> findByEmailPassword(String email,String password) {
        return findAll().stream()
                .filter(p -> p.getEmail().equals(email)).findFirst();
    }
}
