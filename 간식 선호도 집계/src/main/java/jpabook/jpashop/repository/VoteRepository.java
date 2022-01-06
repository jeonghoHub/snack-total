package jpabook.jpashop.repository;

import jpabook.jpashop.snackDomain.SnackTotal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class VoteRepository {
    private final EntityManager em;

    public Long thisMonthSnackChk(String userId) {
        LocalDate currentDate = LocalDate.now();
        String yearMonth = currentDate.toString().substring(0,7);

        TypedQuery<Long> query = em.createQuery("select " +
                "count(*) " +
                "from " +
                "SnackTotal st " +
                "join st.user " +
                "where date_format(created_date, '%Y-%m') = :yearMonth and member_id = :userId ", Long.class);
        Long result = query
                .setParameter("yearMonth",yearMonth)
                .setParameter("userId", userId)
                .getSingleResult();
        return result;
    }
}
