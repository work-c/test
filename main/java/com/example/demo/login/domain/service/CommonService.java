package com.example.demo.login.domain.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.repository.UserDao;

/**
 * @author work
 *
 */

@Service
public class CommonService {

	@Autowired
	@Qualifier("UserDaoJdbcImpl")
	UserDao dao;



	/**
	 * @return
	 * 件数カウント
	 */

	public int count() {
		return dao.count();
	}



	/**
	 * @return
	 * 結婚用ラジオボタン設定値
	 */

	public Map<String, String> initRadioMarrige() {
		Map<String, String> radio = new LinkedHashMap<>();

		radio.put("既婚", "true");
		radio.put("未婚", "false");

		return radio;
	}

}
