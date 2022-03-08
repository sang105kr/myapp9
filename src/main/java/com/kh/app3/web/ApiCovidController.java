package com.kh.app3.web;

import com.kh.app3.domain.covid.svc.CovidSVC;
import com.kh.app3.domain.covid.svc.Response;
import com.kh.app3.web.api.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/covid")
public class ApiCovidController {

  private final CovidSVC covidSVC;

//  public ApiCovidController(CovidSVC covidSVC){
//    this.covidSVC = covidSVC;
//  }
  @ResponseBody
  @GetMapping
  public ApiResult<Object> covidInfo(
      @RequestParam String sdate,
      @RequestParam String edate
      ){
    Response res = covidSVC.getCovidInfo(sdate,edate);
    return new ApiResult<>("00","success",res.getBody());
  }
}








