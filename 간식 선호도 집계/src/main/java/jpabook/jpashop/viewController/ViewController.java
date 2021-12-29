package jpabook.jpashop.controller;

import jpabook.jpashop.service.LoginService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor

public class ViewController {

    @RequestMapping("/home")
    public String home() {
        return "home";
    }
}
