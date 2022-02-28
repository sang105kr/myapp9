package com.kh.app3.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/map")
public class MapController {

  @GetMapping("")
  public String map(){

    return "map/test";
  }
}
