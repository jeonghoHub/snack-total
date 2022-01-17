package jpabook.jpashop.controller;

import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.LoginRepository;
import jpabook.jpashop.repository.profileRepository;
import jpabook.jpashop.snackDomain.SnackItem;
import jpabook.jpashop.snackDomain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class profileController {
    private final profileRepository profileRepository;
    private final LoginRepository loginRepository;

    @GetMapping("/profile")
    public String profile() {
        return "profile/profile";
    }

    @Transactional
    @PostMapping("/profile/passwordChk")
    public ResponseEntity<?> passwordChk(@RequestParam("img-file") MultipartFile files,
                                         HttpServletRequest request,
                                         @RequestParam String password,
                                         @RequestParam String newPassword) {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        System.out.println("!@@@@@@@@"+newPassword);
        String filePath = files.getOriginalFilename();
        System.out.println("@@@@@@@@@@@@@@@@  >>> " + files.getOriginalFilename());

        String baseDir = "C:\\Users\\abc\\Desktop\\github간식 선호도 프로젝트\\간식 선호도 집계\\src\\main\\resources\\static\\image";
        String uplodadfilePath = baseDir + "\\" + files.getOriginalFilename();
        String showFilePath = "/image/"+files.getOriginalFilename();
        try {
            files.transferTo(new File(uplodadfilePath));
        } catch (IOException e) {
            if(files.getOriginalFilename().equals("")){
                showFilePath = "/image/default.png";
            } else {
                showFilePath = "/image/"+files.getOriginalFilename();
            }
        }

        Message message = new Message();

        try{
            List<User> user = profileRepository.passwordCheck(userId, password);
            System.out.println("!@@#!@#"+user.get(0).getName());
            if(!newPassword.isEmpty()) {
                System.out.println("여기를 왜타냐고!!!!!!!!!!!!!!!!!");
                user.get(0).setPassword(newPassword);
            }
                user.get(0).setProfile_img_path(showFilePath);

            message.setData(true);
        }catch (IndexOutOfBoundsException e) {
            message.setData(false);
        }
        return ResponseEntity.ok(message);
    }

}
