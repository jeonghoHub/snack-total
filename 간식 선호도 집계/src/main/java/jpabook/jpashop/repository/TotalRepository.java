package jpabook.jpashop.repository;

import jpabook.jpashop.snackDomain.SnackItem;
import jpabook.jpashop.snackDomain.SnackTotal;
import jpabook.jpashop.snackDomain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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

    public List<SnackItem> searchSnack (String name, String category) {
        String jpql = "select s from SnackItem s where 1=1";

        if(name != "" && name != null){
            jpql += " and s.name like concat('%',:name,'%') ";
        }
        if(category != "" && category != null) {
            jpql += " and s.cateGory = :category ";
        }
        TypedQuery<SnackItem> query = em.createQuery(jpql, SnackItem.class);

        if(name != "" && name != null){
            query = query.setParameter("name", name);
        }
        if(category != "" && category != null){
            query = query.setParameter("category", category);
        }

        return query.getResultList();
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
