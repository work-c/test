package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

/**
 * @author work
 *
 */

@Repository("UserDaoJdbcImpl")
public class UserDaoJdbcImpl implements UserDao {

	@Autowired
	JdbcTemplate jdbc;

	@Autowired
	PasswordEncoder passwordEncoder;



	/**
	 * 件数カウント
	 */

	@Override
	public int count() throws DataAccessException {

		int count = jdbc.queryForObject("SELECT COUNT(*) FROM m_user", Integer.class);
		return count;
	}



	/**
	 * １ユーザー追加
	 */

	@Override
	public int insertOne(User user) throws DataAccessException {

		String password = passwordEncoder.encode(user.getPassword());

		int rowNumber = jdbc.update("INSERT INTO m_user(user_id,"
				+ " password,"
				+ " user_name,"
				+ " birthday,"
				+ " age,"
				+ " marrige,"
				+ " role)"
				+ " VALUES(?,?,?,?,?,?,?)"
				, user.getUserId()
				, password
				, user.getUserName()
				, user.getBirthday()
				, user.getAge()
				, user.isMarrige()
				, user.getRole()
			);

		return rowNumber;
	}



	/**
	 * ユーザーテーブルに１ユーザー追加成功後、詳細テーブルに１ユーザーを追加
	 */

	@Override
	public int insertOneAfter(User user) throws DataAccessException {

		int rowNumber = jdbc.update("INSERT INTO a_user(id, user_id)"
				+ " SELECT id, user_id FROM m_user WHERE user_id = ?"
				, user.getUserId()
			);

		return rowNumber;
	}



	/**
	 * １ユーザー検索
	 */

	@Override
	public User selectOne(String userId) throws DataAccessException {

		Map<String, Object> map = jdbc.queryForMap("SELECT * FROM m_user" + " WHERE user_Id = ?", userId);

		User user = new User();

		user.setId((Integer)map.get("id"));
		user.setUserId((String)map.get("user_id"));
		user.setPassword((String)map.get("password"));
		user.setUserName((String)map.get("user_name"));
		user.setBirthday((Date)map.get("birthday"));
		user.setAge((Integer)map.get("age"));
		user.setMarrige((Boolean)map.get("marrige"));
		user.setRole((String)map.get("role"));

		return user;
	}



	/**
	 * 全ユーザー検索
	 */

	@Override
	public List<User> selectMany() throws DataAccessException {

		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM m_user");

		List<User> userList = new ArrayList<>();

		for(Map<String, Object> map : getList) {
			User user = new User();

			user.setId((Integer)map.get("id"));
			user.setUserId((String)map.get("user_id"));
			user.setPassword((String)map.get("password"));
			user.setUserName((String)map.get("user_name"));
			user.setBirthday((Date)map.get("birthday"));
			user.setAge((Integer)map.get("age"));
			user.setMarrige((Boolean)map.get("marrige"));
			user.setRole((String)map.get("role"));

			userList.add(user);
		}

		return userList;
	}



	/**
	 * 1ユーザー更新
	 */

	@Override
	public int updateOne(User user) throws DataAccessException {

		String password = passwordEncoder.encode(user.getPassword());

		int rowNumber = jdbc.update("UPDATE m_user "
				+ "SET "
				+ "user_id = ?,"
				+ "password = ?,"
				+ "user_name = ?,"
				+ "birthday = ?,"
				+ "age = ?,"
				+ "marrige = ?,"
				+ "role = ?"
				+ " WHERE id = ?"
				, user.getUserId()
				, password
				, user.getUserName()
				, user.getBirthday()
				, user.getAge()
				, user.isMarrige()
				, user.getRole()
				, user.getId());

		//if(rowNumber > 0) {
			//throw new DataAccessException("トランザクションテスト") {};
		//}

		return rowNumber;
	}



	/**
	 * 1ユーザー削除
	 */

	/** 削除処理を管理者のみへ変更
	@Override
	public int deleteOne(String userId) throws DataAccessException {
		int rowNumber = jdbc.update("DELETE FROM m_user WHERE user_id = ?", userId);
		return rowNumber;
	}
	*/



	/**
	 * ユーザーリストCSV出力：　※　ローカル環境のみ
	 */

	/** オンライン環境での動作確認が取れていないので停止
	@Override
	public void userCsvOut() throws DataAccessException {

		String sql = "SELECT * FROM m_user";
		UserRowCallbackHandler handler = new UserRowCallbackHandler();
		jdbc.query(sql, handler);

	}
	*/

}
