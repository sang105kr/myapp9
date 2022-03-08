package com.kh.app3.domain.covid.svc;

import java.time.LocalDate;

public interface CovidSVC {
  Response getCovidInfo(String startDate, String endDate);
}
