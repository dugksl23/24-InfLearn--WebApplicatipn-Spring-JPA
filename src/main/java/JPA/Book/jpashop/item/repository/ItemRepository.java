package JPA.Book.jpashop.item.repository;


import JPA.Book.jpashop.item.domain.Item;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    //@PersistenceContext SpringJPA가 스프링 빈으로 등록
    private final EntityManager em;

    public Long saveItem(Item item) {
        if(item.getId() == null) {
            em.persist(item);
            return item.getId();
            //item은 JPA에 저장하기 전까지는 id 값이 없기에 영속화 및 db와 동기화를 먼저!
        } else {
            em.merge(item);
            return item.getId();//변경된 Entity를 DB에 병합 (update)
        }
    }

    public Item getItemById(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> getAllItems() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }

}
