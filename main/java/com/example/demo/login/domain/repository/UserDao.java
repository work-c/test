package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.User;

/**
 * @author work
 *
 */

public interface UserDao {

	public int count() throws DataAccessException;

	public int insertOne(User user) throws DataAccessException;

	public int insertOneAfter(User user) throws DataAccessException;

	public User selectOne(String userId) throws DataAccessException;

	public List<User> selectMany() throws DataAccessException;

	public int updateOne(User user) throws DataAccessException;

	// 削除処理を管理者のみへ変更
	//public int deleteOne(String userId) throws DataAccessException;

	// オンライン環境での動作確認が取れていないので停止
	//public void userCsvOut() throws DataAccessException;

}
