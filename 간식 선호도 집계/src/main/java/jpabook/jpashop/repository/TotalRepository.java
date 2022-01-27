package jpabook.jpashop.repository;

import jpabook.jpashop.snackDomain.SnackItem;
import jpabook.jpashop.snackDomain.SnackTotal;
import jpabook.jpashop.snackDomain.User;
import jpabook.jpashop.snackDomain.voteListDto;
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

    public List<voteListDto> searchSnack (String name, String category) {
        String jpql = "select\n" +
                "\tsi.file_path as filePath,\n" +
                "\tsi.name as name,\n" +
                "\tsi.snack_id as itemId,\n" +
                "\tu.user_id as userId,\n" +
                "\tu.name as createUser\n" +
                "from\n" +
                "\tsnack_item si\n" +
                "left join user u on\n" +
                "\tsi.create_user = u.user_id " +
                "where 1=1 ";

        if(name != "" && name != null){
            jpql += " and si.name like concat('%',:name,'%') ";
        }
        if(category != "" && category != null) {
            jpql += " and si.cate_gory = :category ";
        }
        Query query = em.createNativeQuery(jpql, "voteListMapping");

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
