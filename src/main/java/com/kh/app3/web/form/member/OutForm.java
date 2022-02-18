package com.kh.app3.web.form.member;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class OutForm {
  @NotBlank
  @Email
  @Size(min=4, max=50)
  private String email;
  @NotBlank
  @Size(min=4, max=12)
  private String passwd;

  private Boolean agree;
}
