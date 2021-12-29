package jpabook.jpashop.repository;

import jpabook.jpashop.snackDomain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class LoginRepository {
    private final EntityManager em;

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public List<User> login(User user){
        return em.createQuery("select u from User u where u.id = :userId and u.password = :password", User.class)
                .setParameter("userId", user.getId())
                .setParameter("password", user.getPassword())
                .getResultList();
    }
}
