package jpabook.jpashop.repository;


import jpabook.jpashop.controller.SnackItemForm;
import jpabook.jpashop.snackDomain.SnackItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(SnackItem SnackItem) {
        if(SnackItem.getId() == null) {
            em.persist(SnackItem);
        }
    }

    public SnackItem findOne(Long id) {
        return em.find(SnackItem.class, id);
    }

    public List<SnackItem> findAll(){
        return em.createQuery("select s from SnackItem s ORDER BY s.name", SnackItem.class)
                .getResultList();
    }

    public void deleteOne(Long itemId){
        SnackItem snackItem = em.find(SnackItem.class, itemId);
        em.remove(snackItem);
    }
}
