package com.example.demo.login.domain.service;

import java.util.List;

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
import com.example.demo.login.domain.repository.UserDao;


/**
 * @author work
 *
 */

@Transactional
@Service
public class UserService {

	@Autowired
	@Qualifier("UserDaoJdbcImpl")
	UserDao dao;

	@Autowired
	PlatformTransactionManager txManager;



	/**
	 * @param user
	 * @return
	 * １ユーザー追加
	 */

	public boolean insert(User user) {

		int rowNumber = dao.insertOne(user);

		boolean result = false;

		if(rowNumber > 0) {
			result = true;
			dao.insertOneAfter(user);
		}

		return result;
	}



	/**
	 * @return
	 * 全ユーザー検索
	 */

	public List<User> selectMany() {
		return dao.selectMany();
	}



	/**
	 * @param userId
	 * @return
	 * １ユーザー検索
	 */

	public User selectOne(String userId) {
		return dao.selectOne(userId);
	}



	/**
	 * @param user
	 * @return
	 * 1ユーザー更新
	 */

	public boolean updateOne(User user) {

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		def.setName("UpdateUser");
		def.setReadOnly(false);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		TransactionStatus status = txManager.getTransaction(def);
		boolean result = false;

		try {

			int rowNumber = dao.updateOne(user);

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

	/** 削除処理を管理者のみへ変更
	public boolean deleteOne(String userId) {

		int rowNumber = dao.deleteOne(userId);
		boolean result = false;

		if(rowNumber > 0) {
			result = true;
		}

		return result;
	}
	**/



	/**
	 * @throws DataAccessException
	 * ユーザーリストCSV出力：　※　ローカル環境のみ
	 */

	/** オンライン環境での動作確認が取れていないので停止
	public void userCsvOut() throws DataAccessException {
		dao.userCsvOut();
	}
	*/



	/**
	 * @param fileName
	 * @return
	 * @throws IOException
	 *
	 */

	/** オンライン環境での動作確認が取れていないので停止
	public byte[] getFile(String fileName) throws IOException {
		FileSystem fs = FileSystems.getDefault();
		Path p = fs.getPath(fileName);
		byte[] bytes = Files.readAllBytes(p);

		return bytes;
	}
	*/

}
