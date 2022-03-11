package com.kh.app3.domain.bbs.dao;

public enum BbsStatus {
  D("삭제"),I("임시저장"),W("경고");

  private final String description;

  BbsStatus(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
