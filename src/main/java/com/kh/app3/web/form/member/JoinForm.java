package com.kh.app3.web.form.member;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class JoinForm {
  @NotBlank
  @Email
  @Size(min=4, max=50)
  private String email;
  @NotBlank
  @Size(min=4, max=12)
  private String passwd;
  @NotBlank
  @Size(min=4, max=12)
  private String passwdChk;
  @NotBlank
  @Size(min=4, max=30)
  private String nickname;
}
