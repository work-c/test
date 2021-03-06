package com.example.demo.login.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.RestService;

/**
 * @author work
 *
 */

@RestController
public class UserRestController {

	@Autowired
	RestService service;



	/**
	 * @return
	 */

	@GetMapping("/rest/get")
	public List<User> getUserMany() {
		return service.selectMany();
	}


	/**
	 * @param userId
	 * @return
	 */

	@GetMapping("/rest/get/{userId:.+}")
	public User getUserOne(@PathVariable("userId") String userId) {
		return service.selectOne(userId);
	}



	/**
	 * @param user
	 * @return
	 */

	@PostMapping("/rest/insert")
	public String postUserOne(@RequestBody User user) {

		boolean result = service.insert(user);
		String str = "";

		if(result == true) {
			str = "{\"result\":\"ok\"}";
		} else {
			str = "{\"result\":\"error\"}";
		}

		return str;
	}



	/**
	 * @param user
	 * @return
	 */

	@PutMapping("/rest/update")
	public String putUserOne(@RequestBody User user) {

		boolean result = service.update(user);
		String str = "";

		if(result == true) {
			str = "{\"result\":\"ok\"}";
		} else {
			str = "{\"result\":\"error\"}";
		}

		return str;
	}



	/**
	 * @param userId
	 * @return
	 */

	@DeleteMapping("/rest/delete/{userId:.+}")
	public String deleteUserOne(@PathVariable("userId") String userId) {

		boolean result = service.delete(userId);
		String str = "";

		if(result == true) {
			str = "{\"result\":\"ok\"}";
		} else {
			str = "{\"result\":\"error\"}";
		}

		return str;
	}
}
