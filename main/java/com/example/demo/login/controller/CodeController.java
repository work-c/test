package com.example.demo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author work
 * コード管理画面コントローラー
 */

@Controller
public class CodeController {

	/**
	 * @param model
	 * @return
	 * コード管理画面
	 */

	@GetMapping("/codeHome")
	public String getCodeHome(Model model) {

		model.addAttribute("contents", "login/codeHome :: codeHome_contents");
		return "login/homeLayout";
	}

}
