package com.example.demo.login.domain.repository;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.User;

/**
 * @author work
 *
 */

public interface AdminDao {

	public int insertOneAdmin(User user) throws DataAccessException;

	public User selectOneAdmin(String userId) throws DataAccessException;

	public int updateOneAdmin(User admin) throws DataAccessException;

	public int deleteOneAdmin(String userId) throws DataAccessException;

}
