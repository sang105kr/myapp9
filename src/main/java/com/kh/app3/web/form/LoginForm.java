package com.kh.app3.web.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginForm {

  @NotBlank // null아니고 적어도 공백문자가 아닌문자가 1개이상인지 체크
  @Email    // 이메일 형식인지 체크
  @Size(min = 4, max=50)
  private String email;

  @NotBlank
  @Size(min = 4, max=8)
  private String passwd;
}
