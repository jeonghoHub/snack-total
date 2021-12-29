package jpabook.jpashop.controller;

import jpabook.jpashop.service.LoginService;
import jpabook.jpashop.snackDomain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@Getter
@Setter
public class LoginController {
    private final LoginService loginService;

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/api/login")
    public ResponseEntity<Message> loginCheck(HttpServletRequest request, HttpServletResponse response, Model model, User user) {

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            List<User> login = loginService.login(user);
            message.setStatus(StatusEnum.OK);
            message.setMessage("성공코드");
            HttpSession session = request.getSession();
            session.setAttribute("userId", login.get(0).getId());
        } catch (Exception e) {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("실패코드");
        } finally {
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        }
    }
}
