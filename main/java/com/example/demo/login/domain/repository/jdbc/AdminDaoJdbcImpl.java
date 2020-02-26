package com.example.demo.login.domain.repository.jdbc;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.AdminDao;

/**
 * @author work
 *
 */

@Repository("AdminDaoJdbcImpl")
public class AdminDaoJdbcImpl implements AdminDao {

	@Autowired
	JdbcTemplate jdbc;

	@Autowired
	PasswordEncoder passwordEncoder;



	/**
	 * @param form
	 * @param model
	 * @return
	 * ユーザー登録画面：管理者用
	 */

	/**
	 * １ユーザー追加：管理者用
	 */

	@Override
	public int insertOneAdmin(User user) throws DataAccessException {

		String password = passwordEncoder.encode(user.getPassword());

		int rowNumber = jdbc.update("INSERT"
				+ " INTO m_user(user_id, password, user_name, birthday, age, marrige, role)"
				+ " VALUES(?,?,?,?,?,?,?)"
				, user.getUserId()
				, password
				, user.getUserName()
				, user.getBirthday()
				, user.getAge()
				, user.isMarrige()
				, user.getRole()
			);

		if(rowNumber > 0) {

			rowNumber = jdbc.update("INSERT INTO a_user(id, user_id, address, tel)"
					+ " VALUES("
					+ "(SELECT id FROM m_user WHERE user_id = ?),"
					+ "?,?,?)"
					, user.getUserId()
					, user.getUserId()
					, user.getAddress()
					, user.getTel()
			);

		}

		return rowNumber;
	}



	/**
	 * １ユーザー検索：管理者用
	 */

	@Override
	public User selectOneAdmin(String userId) throws DataAccessException {

		Map<String, Object> map = jdbc.queryForMap("SELECT"
		+	" m.id, m.user_id, password, user_name, birthday, age, marrige, role, address, tel"
		+ " FROM m_user AS m INNER JOIN a_user AS a ON m.user_id = a.user_id"
		+ " WHERE m.user_id = ? ORDER BY m.user_id", userId);

		User user = new User();

		user.setId((Integer)map.get("id"));
		user.setUserId((String)map.get("user_id"));
		user.setPassword((String)map.get("password"));
		user.setUserName((String)map.get("user_name"));
		user.setBirthday((Date)map.get("birthday"));
		user.setAge((Integer)map.get("age"));
		user.setMarrige((Boolean)map.get("marrige"));
		user.setRole((String)map.get("role"));
		user.setAddress((String)map.get("address"));
		user.setTel((String)map.get("tel"));

		return user;
	}



	/**
	 * 1ユーザー更新：管理者用
	 */

	@Override
	public int updateOneAdmin(User user) throws DataAccessException {

		String password = passwordEncoder.encode(user.getPassword());

		int rowNumber = jdbc.update("UPDATE m_user AS m"
				+ " INNER JOIN a_user AS a ON m.user_id = a.user_id"
				+ " SET m.user_id = ?, password = ?, user_name = ?, birthday = ?, age = ?,"
				+ " marrige = ?, role = ?, address = ?, tel = ?, a.user_id = ? WHERE m.id = ?"
				, user.getUserId()
				, password
				, user.getUserName()
				, user.getBirthday()
				, user.getAge()
				, user.isMarrige()
				, user.getRole()
				, user.getAddress()
				, user.getTel()
				, user.getUserId()
				, user.getId());

		//if(rowNumber > 0) {
			//throw new DataAccessException("トランザクションテスト") {};
		//}

		return rowNumber;
	}



	/**
	 * 1ユーザー削除：管理者用
	 */

	@Override
	public int deleteOneAdmin(String userId) throws DataAccessException {

		int rowNumber = jdbc.update("DELETE FROM m_user WHERE user_id = ?", userId);

		if(rowNumber > 0) {

			rowNumber = jdbc.update("DELETE FROM a_user WHERE user_id = ?", userId);

		}

		return rowNumber;
	}





}
