package com.example.demo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



/**
 * @author work
 * ログイン用コントローラー
 */

@Controller
public class LoginController {



	/**
	 * @param model
	 * @return
	 * ログイン画面
	 */

	@GetMapping("/login")
	public String getLogin(Model model) {

		return "login/login";
	}



	/**
	 * @param model
	 * @return
	 * ホーム画面からログイン画面へリダイレクト
	 */

	@PostMapping("/login")
	public String postLogin(Model model) {

		return "redirect:/home";
	}



	/**
	 * @param model
	 * @return
	 * ホーム画面
	 */

	@GetMapping("/home")
	public String getHome(Model model) {

		model.addAttribute("contents", "login/home :: home_contents");
		return "login/homeLayout";
	}



	/**
	 * @return
	 * ログアウトしログイン画面へ遷移
	 */

	@PostMapping("/logout")
	public String postLogout() {

		return "redirect:/login";
	}



}



