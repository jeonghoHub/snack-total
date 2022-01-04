package jpabook.jpashop.service;

import jpabook.jpashop.snackDomain.SnackTotal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class AuthorityService {
    private final EntityManager em;

    public void findOne(SnackTotal snackTotal) {
        em.persist(snackTotal);
    }

    public void save(SnackTotal snackTotal) {
        em.persist(snackTotal);
    }
}
