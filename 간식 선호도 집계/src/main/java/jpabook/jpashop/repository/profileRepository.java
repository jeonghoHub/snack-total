package jpabook.jpashop.repository;

import jpabook.jpashop.snackDomain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class profileRepository {
    private final EntityManager em;

    public List<User> passwordCheck(String userId, String password) {
        return em.createQuery("select u from User u where u.id = :userId and u.password = :password" ,User.class)
                .setParameter("userId", userId)
                .setParameter("password", password)
                .getResultList();
    }
}
