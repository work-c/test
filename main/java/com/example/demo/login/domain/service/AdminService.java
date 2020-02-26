package com.example.demo.login.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.AdminDao;

@Transactional
@Service
public class AdminService {

	@Autowired
	@Qualifier("AdminDaoJdbcImpl")
	AdminDao dao;

	@Autowired
	PlatformTransactionManager txManager;



	/**
	 * @param user
	 * @return
	 * １ユーザー追加：管理者用
	 */

	public boolean insertAdmin(User user) {

		int rowNumber = dao.insertOneAdmin(user);

		boolean result = false;

		if(rowNumber > 0) {
			result = true;
		}

		return result;
	}



	/**
	 * @param userId
	 * @return
	 * １ユーザー検索：管理者用
	 */

	public User selectOneAdmin(String userId) {
		return dao.selectOneAdmin(userId);
	}



	/**
	 * @param user
	 * @return
	 * 1ユーザー更新：管理者用
	 */

	public boolean updateOneAdmin(User user) {

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		def.setName("UpdateUser");
		def.setReadOnly(false);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		TransactionStatus status = txManager.getTransaction(def);
		boolean result = false;

		try {

			int rowNumber = dao.updateOneAdmin(user);

			if(rowNumber > 0) {
				result = true;
			}

		} catch(Exception e) {

			txManager.rollback(status);
			throw new DataAccessException("ERROR Update", e) {};

		}

		txManager.commit(status);

		return result;
	}



	/**
	 * @param userId
	 * @return
	 * 1ユーザー削除
	 */

	public boolean deleteOneAdmin(String userId) {

		int rowNumber = dao.deleteOneAdmin(userId);
		boolean result = false;

		if(rowNumber > 0) {
			result = true;
		}

		return result;
	}


}
