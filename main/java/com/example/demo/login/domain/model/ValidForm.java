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

@Data
public class ValidForm {

    //必須入力、メールアドレス形式
    @NotBlank(groups = ValidGroup1.class)
    @Email(groups = ValidGroup2.class)
    private String userId; // ユーザーID

    //必須入力、長さ4から100桁まで、半角英数字のみ
    @NotBlank(groups = ValidGroup1.class)
    @Length(min = 4, max = 100, groups = ValidGroup2.class)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", groups = ValidGroup3.class)
    private String password; // パスワード

    //必須入力
    @NotBlank(groups = ValidGroup1.class)
    private String userName; // ユーザー名

    //必須入力
    @NotNull(groups = ValidGroup1.class)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date birthday; // 誕生日

    //値が20から100まで
    @Min(value = 20, groups = ValidGroup2.class)
    @Max(value = 100, groups = ValidGroup2.class)
    private int age; // 年齢

    private boolean marrige; // 結婚ステータス

  	private String role;

}