package jpabook.jpashop.controller;


import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.VoteRepository;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.TotalService;
import jpabook.jpashop.snackDomain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class VoteController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;
    private final TotalService totalService;
    private final VoteRepository voteRepository;

    @GetMapping("/votePage")
    public String createForm() {
        return "vote/votePage";
    }

    @GetMapping("/vote/userVotePage")
    public String userVotePage() {
        return "vote/userVotePage";
    }

    @GetMapping("/vote/userVoteList")
    public ResponseEntity<?> userVoteList(HttpServletRequest request, VoteListDto param) {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        List<HashMap<Object, String>> hashMaps;
        hashMaps = voteRepository.userVoteList(userId, param.getYear(), param.getCategory(), param.getName());
        System.out.println("!!@#!@#"+hashMaps);
        return ResponseEntity.ok(hashMaps);
    }


    @GetMapping("/votePage/items")
    public ResponseEntity<?> list() {
        List<voteListDto> voteListDtos = voteRepository.voteList();
        return ResponseEntity.ok(voteListDtos);
    }

    @GetMapping("/votePage/search/items")
    public ResponseEntity<?> searchList(SnackItemForm snackItemForm) {
        List<voteListDto> items;
        System.out.println(">>>>>>>" + snackItemForm.getName());

        items = totalService.searchSnack(snackItemForm.getName(), snackItemForm.getCategory());

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

    @GetMapping("/votePage/snackRanking")
    public ResponseEntity<?>  snackRanking() {
        List<voteRankingDto> query = voteRepository.thisMonthSnackRanking();
        return ResponseEntity.ok(query);
    }

    @GetMapping("/votePage/myChooseSnack")
    public ResponseEntity<?> myChooseSnack(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        List chooseSnackFilePath = voteRepository.thisMonthMyChooseSnack(userId);
        return ResponseEntity.ok(chooseSnackFilePath);
    }
}
