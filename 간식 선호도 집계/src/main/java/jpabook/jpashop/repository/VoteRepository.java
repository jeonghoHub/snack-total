package jpabook.jpashop.repository;

import jpabook.jpashop.controller.VoteListDto;
import jpabook.jpashop.snackDomain.voteRankingDto;
import jpabook.jpashop.snackDomain.voteListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class VoteRepository {
    private final EntityManager em;
    LocalDate currentDate = LocalDate.now();
    String yearMonth = currentDate.toString().substring(0,7);

    public List<HashMap<Object, String>> userVoteList(String userId, String date, String category, String name) {
        String jpql = "select " +
                "date_format(st.created_date, '%Y-%m-%d') as created_date, " +
                "si.cate_gory, " +
                "si.name " +
                "from " +
                "snack_item si " +
                "join snack_total st " +
                "on " +
                "si.snack_id = st.snack_id " +
                "where member_id = :userId ";

        if(date != "" && date != null){
            jpql += " and date_format(st.created_date, '%Y') = :created_date ";
        }
        if(name != "" && name != null){
            jpql += " and si.name like concat('%',:name,'%') ";
        }
        if(category != "" && category != null) {
            jpql += " and si.cate_gory = :category ";
        }
        jpql += " order by created_date desc ;";

        Query query = em.createNativeQuery(jpql).setParameter("userId", userId);

        if(date != "" && date != null){
            query = query.setParameter("created_date", date);
        }
        if(name != "" && name != null){
            query = query.setParameter("name", name);
        }
        if(category != "" && category != null) {
            query = query.setParameter("category", category);
        }

        return query.getResultList();
    }

    public Long thisMonthSnackChk(String userId) {
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

    public  List<voteRankingDto> thisMonthSnackRanking() {
        Query query = em.createNativeQuery("select " +
                "count(si.name) as count, " +
                "si.cate_gory, " +
                "si.name, " +
                "si.file_path as filePath " +
                "from " +
                "snack_item si " +
                "join snack_total st " +
                "on si.snack_id = st.snack_id " +
                "where " +
                "date_format(st.created_date, '%Y-%m') = :yearMonth " +
                "group by name " +
                "order by count desc", "snackRankingMapping");
        List<voteRankingDto> result = query.setParameter("yearMonth",yearMonth).getResultList();

        return result;
    }

    public List<voteListDto> voteList() {
        Query query = em.createNativeQuery("select\n" +
                "\tsi.file_path as filePath,\n" +
                "\tsi.name as name,\n" +
                "\tsi.snack_id as itemId,\n" +
                "\tu.user_id as userId,\n" +
                "\tu.name as createUser\n" +
                "from\n" +
                "\tsnack_item si\n" +
                "left join user u on\n" +
                "\tsi.create_user = u.user_id", "voteListMapping");
        List result = query.getResultList();
        return result;
    }

    public List thisMonthMyChooseSnack(String userId) {
        Query query = em.createNativeQuery("select\n" +
                "\tsi.name as name, \n" +
                "\tsi.snack_id as id\n" +
                "from\n" +
                "\tsnack_item si\n" +
                "join snack_total st on\n" +
                "\tst.snack_id = si.snack_id\n" +
                "where\n" +
                "date_format(st.created_date, '%Y-%m') = :yearMonth " +
                "\tand st.member_id = :userId\n" +
                "limit 1").setParameter("yearMonth",yearMonth)
                          .setParameter("userId", userId);
        List result = query.getResultList();
        return result;
    }
}
