package com.example.demo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CodeController {

	@GetMapping("/codeHome")
	public String getCodeHome(Model model) {

		model.addAttribute("contents", "login/codeHome :: codeHome_contents");
		return "login/homeLayout";
	}

}
