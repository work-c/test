package com.example.demo.login.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 *	ユーザー用画面コントローラー
 */
@Controller
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	SelectService selectService;

	private Map<String, String> initMarrige;
	private String keepRole;



	/**
	 * @param model
	 * @return ユーザー用ユーザー一覧画面
	 */
	@GetMapping("/userList")
	public String getUserList(Model model) {
		model.addAttribute("contents", "login/userList :: userList_contents");

		List<User> userList = userService.selectMany();

		model.addAttribute("userList", userList);

		int count = userService.count();
		model.addAttribute("userListCount", count);

		return "login/homeLayout";
	}



	/**
	 * @param form
	 * @param model
	 * @return ユーザー用ユーザー登録画面
	 */
	@GetMapping("/userCreate")
	public String getUserCreate(@ModelAttribute ValidForm form, Model model) {
		model.addAttribute("contents", "login/userCreate :: userCreate_contents");

		initMarrige = selectService.initRadioMarrige();
		model.addAttribute("radioMarrige", initMarrige);

		return "login/homeLayout";
	}



	/**
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @return ユーザー用ユーザー登録実行
	 */
	@PostMapping(value="/userCreate", params="insert")
	public String postUserCreate(@ModelAttribute @Validated(GroupOrder.class) ValidForm form,
			BindingResult bindingResult, Model model) {

		if(bindingResult.hasErrors()) {
			return getUserCreate(form, model);
		}

		System.out.println(form);

		User user = new User();

		user.setUserId(form.getUserId());
		user.setPassword(form.getPassword());
		user.setUserName(form.getUserName());
		user.setBirthday(form.getBirthday());
		user.setAge(form.getAge());
		user.setMarrige(form.isMarrige());
		user.setRole("ROLE_GENERAL");

		boolean result = userService.insert(user);

		if(result == true) {
			System.out.println("insert成功");
		} else {
			System.out.println("insert失敗");
		}

		return "redirect:/userList";
	}



	/**
	 * @return
	 */
	@PostMapping(value="/userCreate", params="return")
	public String postReturn() {
		return "redirect:/userList";
	}



	/**
	 * @param form
	 * @param model
	 * @param userId
	 * @return ユーザー用ユーザー一覧画面へ遷移
	 */
	@GetMapping("/userDetail/{id:.+}")
	public String getUserDetail(@ModelAttribute ValidForm form, Model model,
			@PathVariable("id") String userId) {

		model.addAttribute("contents", "login/userDetail :: userDetail_contents");

		initMarrige = selectService.initRadioMarrige();
		model.addAttribute("radioMarrige", initMarrige);

		if(userId != null && userId.length() > 0) {
			User user = userService.selectOne(userId);

			form.setUserId(user.getUserId());
			form.setUserName(user.getUserName());
			form.setBirthday(user.getBirthday());
			form.setAge(user.getAge());
			form.setMarrige(user.isMarrige());
			keepRole = user.getRole();

			model.addAttribute("validForm", form);
		}

		return "login/homeLayout";
	}



	/**
	 * @param model
	 * @return ユーザー用ユーザー詳細画面
	 */
	@GetMapping("/userList/csv")
	public ResponseEntity<byte[]> getUserListCsv(Model model) {

		userService.userCsvOut();
		byte[] bytes = null;

		try {

			bytes = userService.getFile("sample.csv");

		} catch(IOException e) {
			e.printStackTrace();
		}

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", "sample.csv");

		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
	}



	/**
	 * @param form
	 * @param model
	 * @return ユーザー用ユーザー情報更新実行
	 */
	@PostMapping(value="/userDetail", params="update")
	public String postUserDetailUpdate(@ModelAttribute @Validated(GroupOrder.class) ValidForm form,
			BindingResult bindingResult, Model model) {

		if(bindingResult.hasErrors()) {
			return getUserDetail(form, model, form.getUserId());
		}

		User user = new User();

		user.setUserId(form.getUserId());
		user.setPassword(form.getPassword());
		user.setUserName(form.getUserName());
		user.setBirthday(form.getBirthday());
		user.setAge(form.getAge());
		user.setMarrige(form.isMarrige());
		user.setRole(keepRole);

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

		return getUserList(model);
	}



	/**
	 * @param form
	 * @param model
	 * @return ユーザー用ユーザー削除実行
	 */
	@PostMapping(value="/userDetail", params="delete")
	public String postUserDetailDelete(@ModelAttribute ValidForm form, Model model) {

		System.out.println("削除ボタンの処理");

		boolean result = userService.deleteOne(form.getUserId());

		if(result == true) {
			model.addAttribute("result", "削除成功");
		} else {
			model.addAttribute("result", "削除失敗");
		}

		return getUserList(model);
	}



}
