package jpabook.jpashop.service;

import jpabook.jpashop.controller.SnackItemForm;
import jpabook.jpashop.repository.TotalRepository;
import jpabook.jpashop.snackDomain.SnackItem;
import jpabook.jpashop.snackDomain.SnackTotal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TotalService {
    private final TotalRepository totalRepository;

    @Transactional
    public void saveVote(SnackTotal snackTotal) {
        totalRepository.save(snackTotal);
    }

    @Transactional
    public List<SnackItem> searchSnack(String name) {
        return totalRepository.searchSnack(name);
    }

    @Transactional
    public List<Map<Object, String>> snackTotals(){
        List<Map<Object, String>> snackTotals =  totalRepository.totalSnackTotals();
        System.out.println(">>>>>>>>>>>>>" + snackTotals);
        return snackTotals;
    }
}
