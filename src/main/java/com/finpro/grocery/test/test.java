package com.finpro.grocery.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {
  @GetMapping("/test")
  public String testo() {
    return "Hello World!";
  }  
}
