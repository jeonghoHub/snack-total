package jpabook.jpashop.controller;

import jpabook.jpashop.repository.TotalRepository;
import jpabook.jpashop.service.TotalService;
import jpabook.jpashop.snackDomain.SnackTotal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class TotalController {
    private final TotalService totalService;

    @GetMapping("/total")
    public String total(){
        return "total/totalChart";
    }

    @GetMapping("/total/list")
    public ResponseEntity<?> totalList(){
        List<Map<Object, String>> snackTotals = totalService.snackTotals();
        return ResponseEntity.ok(snackTotals);
    }

}
