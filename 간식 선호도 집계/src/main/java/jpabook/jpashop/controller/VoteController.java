package jpabook.jpashop.controller;


import jpabook.jpashop.repository.VoteRepository;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.TotalService;
import jpabook.jpashop.snackDomain.SnackItem;
import jpabook.jpashop.snackDomain.SnackTotal;
import jpabook.jpashop.snackDomain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class VoteController {

    private final ItemService itemService;
    private final TotalService totalService;
    private final VoteRepository voteRepository;

    @GetMapping("/votePage")
    public String createForm() {
        return "vote/votePage";
    }

    @GetMapping("/votePage/items")
    public ResponseEntity<?> list() {
        List<SnackItem> items = itemService.findItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/votePage/search/items")
    public ResponseEntity<?> searchList(SnackItemForm snackItemForm) {
        List<SnackItem> items = null;
        System.out.println(">>>>>>>" + snackItemForm.getName());
        if(snackItemForm.getName().equals("")){
            items = itemService.findItems();
        } else {
            items = totalService.searchSnack(snackItemForm.getName());
        }
        return ResponseEntity.ok(items);
    }

    @PostMapping("/votePage/insert/vote")
    public ResponseEntity<?> insertVote(HttpServletRequest request, SnackItemForm snackItemForm) {
        SnackTotal snackTotal = new SnackTotal();
        SnackItem snackItem = new SnackItem();
        User user = new User();

        LocalDateTime date = LocalDateTime.now();
        snackTotal.setCreatedDate(date);

        snackItem.setId(snackItemForm.getId());
        snackTotal.setSnackItem(snackItem);

        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        user.setId(userId);
        snackTotal.setUser(user);

        totalService.saveVote(snackTotal);
        return ResponseEntity.ok("123");
    }

    @GetMapping("/votePage/monthVoteChk")
    public ResponseEntity<?>  monthVoteCheck(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        Long count = voteRepository.thisMonthSnackChk(userId);
        System.out.println(">>>>>>>>COUNT>>>>>>" + count);
        return ResponseEntity.ok(count);
    }
}
