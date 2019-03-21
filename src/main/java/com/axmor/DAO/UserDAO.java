package com.axmor.DAO;

import com.axmor.Models.User;

/**
 * Interface for DAO of User Entity.
 *
 * @author  Mikhail Sotnikov
 */
public interface UserDAO {
    /**
     * Create new user by entity
     *
     * @param user for create
     */
    void add(User user);

    /**
     * Get user entity by login
     *
     * @param login Login of user
     * @return User Entity
     */
    User getByLogin(String login);

    /**
     * Update user by entity
     *
     * @param user for update
     */
    void merge(User user);
}
