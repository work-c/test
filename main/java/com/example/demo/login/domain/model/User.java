package com.example.demo.login.domain.model;

import java.util.Date;

import lombok.Data;

/**
 * @author work
 *
 */

@Data
public class User {

	public User() {}

	private String userId;
	private String password;
	private String userName;
	private Date birthday;
	private int age;
	private boolean marrige;
	private String role;

}
