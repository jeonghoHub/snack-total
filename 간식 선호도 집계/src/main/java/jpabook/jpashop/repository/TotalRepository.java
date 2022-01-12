package jpabook.jpashop.repository;

import jpabook.jpashop.snackDomain.SnackItem;
import jpabook.jpashop.snackDomain.SnackTotal;
import jpabook.jpashop.snackDomain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TotalRepository {
    private final EntityManager em;

    public void save(SnackTotal snackTotal) {
        em.persist(snackTotal);
    }

    public List<SnackItem> searchSnack (String name) {
        return em.createQuery("select s from SnackItem s where s.name like concat('%',:name,'%') ORDER BY s.name", SnackItem.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Map<Object, String>> totalSnackTotals () {
        LocalDate currentDate = LocalDate.now();
        String yearMonth = currentDate.toString().substring(0,7);

         Query query = em.createNativeQuery("select " +
                "count(st.snack_id) as count, " +
                "si.name " +
                "from " +
                "snack_total st " +
                "join snack_item si " +
                "on " +
                "st.snack_id = si.snack_id " +
                "and date_format(created_date, '%Y-%m') = :yearMonth " +
                "group by " +
                "st.snack_id " +
                 "order by count desc " +
                 "Limit 10");
        List result = query.setParameter("yearMonth", yearMonth).getResultList();
         return result;
    }
}
