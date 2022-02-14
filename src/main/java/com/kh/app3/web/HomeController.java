package com.kh.app3.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
public class HomeController {

  @RequestMapping("/")
  public String home(){
    log.trace("trace={}","home()호출됨");
    log.debug("debug={}","home()호출됨");
    log.info("info={}","home()호출됨");
    log.warn("warn={}","home()호출됨");
    log.error("error={}","home()호출됨");
    return "home";   // /resoruces/templates/home.html;
  }

}
