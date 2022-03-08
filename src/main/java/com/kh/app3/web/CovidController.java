package com.kh.app3.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/covid")
public class CovidController {

  @GetMapping
  public String covidInfo(){

    return "covid/covidInfo";
  }
}
