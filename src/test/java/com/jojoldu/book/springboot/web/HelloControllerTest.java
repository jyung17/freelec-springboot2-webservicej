package com.jojoldu.book.springboot.web;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
// @RUnWith(SpringRunner.class)
// 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킨다.
// 여기서는 SpringRunner라는 스프링 실행자를 사용한다.
// 스프링 부트 테스트와 JUnit 사이에 연결자 역할을 한다.
@WebMvcTest(controllers = HelloController.class)
// @WebMvcTest
// 여러 스프링 테스트 어노테이션 중, Web(Spring MVC)에 집중할 수 있는 어노테이션
// 선언할 경우 @Controller, @ControllerAdvice 등을 사용할 수 있다
// 단, @Service, @Component, @Repository 등은 사용할 수 없다.
public class HelloControllerTest {

  @Autowired
  // @Autowired
  // 스프링이 관리하는 빈(Bean)을 주입 받는다
  private MockMvc mvc;
  // 웹API를 테스트할 때 사용한다
  // 스프링 MVC 테스트의 시작점이다.
  // 클래스를 통해 HTTP GET, POST 등에 대한 API 테스트를 할 수 있다.

  @Test
  public void hello_return() throws Exception {
    String hello = "hello";

    mvc.perform(get("/hello"))
        // .perform(get("/hello"))
        // MockMvc를 통해 /hello 주소로 HTTP GET 요청을 한다.
        // 체이닝이 지원되어 아래와 같이 여러 검증 기능을 이어서 선언할 수 있다.
        .andExpect(status().isOk())
        // .andExpect(status().isOk())
        // mvc.perform의 결과를 검증한다.
        // HTTP Header의 Status를 검증한다.
        .andExpect(content().string(hello));
    // .andExpect(content().string(hello));
    // mvc.perform의 결과를 검증한다.
    // 응답 본문의 내용을 검증한다.
    // Controller에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증한다.
  }

  @Test
  public void helloDto가_리턴된다() throws Exception {
    String name = "hello";
    int amount = 1000;

    mvc.perform(
            get("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount)))
        .andExpect(status().isOk())
        .andExpect(result -> jsonPath("$.name", is(name)))
        .andExpect(result -> jsonPath("$.amount", is(amount)));
  }
}
