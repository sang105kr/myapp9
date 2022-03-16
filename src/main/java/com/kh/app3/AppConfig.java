package com.kh.app3;

import com.kh.app3.test.Person;
import com.kh.app3.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoginCheckInterceptor())
        .order(1)                   //인터셉터 실행 순저지정
        .addPathPatterns("/**")     // 인터셉터에 포함시키는 url패턴,루트경로부터 하위경로 모두
        .excludePathPatterns(
            "/",                    //초기화면
            "/login",               //로그인
            "/logout",              //로그아웃
            "/members/add",         //회원가입
            "/css/**",
            "/js/**",
            "/img/**",
            "/error/**"
        );  // 인테셉터에서 제외되는 url패턴
  }
}
