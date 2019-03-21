package com.axmor.DAO;

import com.axmor.Models.Issue;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for DAO of Issue Entity.
 *
 * @author  Mikhail Sotnikov
 */
public interface IssueDAO {
    /**
     * Get List of Issue from database
     *
     * @return List of all Issue.
     */
    List<Issue> getAll();

    /**
     * Get one issue by his id
     *
     * @param id id of Issue
     * @return Issue.
     */
    Issue getByID(int id);

    /**
     * Delete issue by entity
     *
     * @param issue for delete
     */
    void delete(Issue issue);

    /**
     * Update issue by entity
     *
     * @param issue for update
     */
    void merge(Issue issue);
}
