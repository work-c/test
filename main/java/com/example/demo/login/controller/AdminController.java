package com.example.demo.login.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.GroupOrder;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.model.ValidForm;
import com.example.demo.login.domain.service.SelectService;
import com.example.demo.login.domain.service.UserService;



/**
 * @author work
 *	管理者用画面コントローラー
 */
@Controller
public class AdminController {

	@Autowired
	UserService userService;

	@Autowired
	SelectService selectService;

	private Map<String, String> initMarrige;



	/**
	 * @param model
	 * @return 管理者用ユーザー一覧画面
	 */
	@GetMapping("/admin")
	public String getAdmin(Model model) {
		model.addAttribute("contents", "login/admin :: admin_contents");

		List<User> userList = userService.selectMany();

		for(User u : userList) {

			String role = u.getRole();

			switch(role){
			case "ROLE_ADMIN":
				u.setRole("管理者");
				break;
			case "ROLE_GENERAL":
				u.setRole("ユーザー");
				break;
			default:
			}
		}

		model.addAttribute("userList", userList);

		int count = userService.count();
		model.addAttribute("userListCount", count);

		return "login/homeLayout";
	}



	/**
	 * @param form
	 * @param model
	 * @return 管理者用ユーザー登録画面
	 */
	@GetMapping("/adminCreate")
	public String getAdminCreate(@ModelAttribute ValidForm form, Model model) {
		model.addAttribute("contents", "login/adminCreate :: adminCreate_contents");

		initMarrige = selectService.initRadioMarrige();
		model.addAttribute("radioMarrige", initMarrige);

		return "login/homeLayout";
	}



	/**
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @return 管理者用ユーザー登録実行
	 */
	@PostMapping(value="/adminCreate", params="insert")
	public String postAdminCreate(@ModelAttribute @Validated(GroupOrder.class) ValidForm form,
			BindingResult bindingResult, Model model) {

		if(bindingResult.hasErrors()) {
			return getAdminCreate(form, model);
		}

		User user = new User();

		user.setUserId(form.getUserId());
		user.setPassword(form.getPassword());
		user.setUserName(form.getUserName());
		user.setBirthday(form.getBirthday());
		user.setAge(form.getAge());
		user.setMarrige(form.isMarrige());

		String role = form.getRole();

		System.out.println(role);

		switch(role){
			case "ROLE_ADMIN":
				user.setRole("ROLE_ADMIN");
				break;
			case "ROLE_GENERAL":
				user.setRole("ROLE_GENERAL");
				break;
			default:
				user.setRole("ROLE_GENERAL");
		}

		boolean result = userService.insert(user);

		if(result == true) {
			System.out.println("insert成功");
		} else {
			System.out.println("insert失敗");
		}

		return "redirect:/admin";
	}



	/**
	 * @return 管理者用ユーザー一覧画面へ遷移
	 */
	@PostMapping(value="/adminCreate", params="return")
	public String postReturn() {
		return "redirect:/admin";
	}



	/**
	 * @param form
	 * @param model
	 * @param userId
	 * @return 管理者用ユーザー詳細画面
	 */
	@GetMapping("/adminDetail/{id:.+}")
	public String getAdminDetail(@ModelAttribute ValidForm form, Model model,
			@PathVariable("id") String userId) {

		model.addAttribute("contents", "login/adminDetail::adminDetail_contents");

		initMarrige = selectService.initRadioMarrige();
		model.addAttribute("radioMarrige", initMarrige);

		if(userId != null && userId.length() > 0) {
			User user = userService.selectOne(userId);

			form.setUserId(user.getUserId());
			form.setUserName(user.getUserName());
			form.setBirthday(user.getBirthday());
			form.setAge(user.getAge());
			form.setMarrige(user.isMarrige());
			form.setRole(user.getRole());

			model.addAttribute("validForm", form);
		}

		return "login/homeLayout";
	}



	/**
	 * @param form
	 * @param model
	 * @return 管理者用ユーザー情報更新実行
	 */
	@PostMapping(value="/adminDetail", params="update")
	public String postAdminDetailUpdate(@ModelAttribute @Validated(GroupOrder.class) ValidForm form,
			BindingResult bindingResult, Model model) {

		if(bindingResult.hasErrors()) {
			return getAdminDetail(form, model, form.getUserId());
		}

		User user = new User();

		user.setUserId(form.getUserId());
		user.setPassword(form.getPassword());
		user.setUserName(form.getUserName());
		user.setBirthday(form.getBirthday());
		user.setAge(form.getAge());
		user.setMarrige(form.isMarrige());

		String role = form.getRole();

		switch(role){
			case "ROLE_ADMIN":
				user.setRole("ROLE_ADMIN");
				break;
			case "ROLE_GENERAL":
				user.setRole("ROLE_GENERAL");
				break;
			default:
				user.setRole("ROLE_GENERAL");
		}

		try {

			boolean result = userService.updateOne(user);

			if(result == true) {
				model.addAttribute("result", "更新成功");
			} else {
				model.addAttribute("result", "更新失敗");
			}
		} catch(DataAccessException e) {
			model.addAttribute("result", "更新失敗（トランザクションテスト）");
		}

		return getAdmin(model);
	}



	/**
	 * @param form
	 * @param model
	 * @return 管理者用ユーザー削除実行
	 */
	@PostMapping(value="/adminDetail", params="delete")
	public String postUserDetailDelete(@ModelAttribute ValidForm form, Model model) {

		System.out.println("削除ボタンの処理");

		boolean result = userService.deleteOne(form.getUserId());

		if(result == true) {
			model.addAttribute("result", "削除成功");
		} else {
			model.addAttribute("result", "削除失敗");
		}

		return getAdmin(model);
	}

}

