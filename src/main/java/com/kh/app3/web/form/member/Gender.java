package com.kh.app3.web.form.member;

public enum Gender {
  MALE("남자"),FEMALE("여자");

  private final String description;

  Gender(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
