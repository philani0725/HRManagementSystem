package HrManagementSys.persistence;

import HrManagementSys.model.Admin;

import java.util.Optional;

/**
 * Data Access Object for Person objects
 */
public interface AdminDAO extends DAO<Admin> {

    /**
     * Find a person using their email address
     * @param email the email address of the person
     * @return Person wrapped in an Optional, will be empty if the person was not found.
     */
    Optional<Admin> findByEmailPassword(String email,String password);
}
