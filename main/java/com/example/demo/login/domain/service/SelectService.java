package com.example.demo.login.domain.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * @author work
 *
 */

@Service
public class SelectService {

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
