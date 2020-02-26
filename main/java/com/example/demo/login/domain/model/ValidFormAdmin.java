package com.example.demo.login.domain.model;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * @author work
 *
 */

@Data
public class ValidFormAdmin {

		/**
		 *
		 */

		private int id;



		/**
     * ユーザーID：必須入力、メールアドレス形式
     */

    @NotBlank(groups = ValidGroup1.class)
    @Email(groups = ValidGroup2.class)
    private String userId;



    /**
     * パスワード：必須入力、長さ4から100桁まで、半角英数字のみ
     */

    @NotBlank(groups = ValidGroup1.class)
    @Length(min = 4, max = 100, groups = ValidGroup2.class)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", groups = ValidGroup3.class)
    private String password;



    /**
     * ユーザー名：必須入力
     */

    @NotBlank(groups = ValidGroup1.class)
    private String userName;



    /**
     * 誕生日：必須入力
     */

    @NotNull(groups = ValidGroup1.class)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date birthday;



    /**
     * 年齢：値が20から100まで
     */
    @Min(value = 20, groups = ValidGroup1.class)
    @Max(value = 100, groups = ValidGroup1.class)
    private int age;



    /**
     *	結婚ステータス
     */

    private boolean marrige;



  	/**
  	 *	権限
  	 */

  	private String role;



  	/**
  	 *	住所
  	 */

    @NotBlank(groups = ValidGroup1.class)
  	private String address;




  	/**
  	 *	連絡先
  	 */

    @Pattern(regexp = "0\\d{1,3}-\\d{1,4}-\\d{4}", groups = ValidGroup1.class)
  	private String tel;

}