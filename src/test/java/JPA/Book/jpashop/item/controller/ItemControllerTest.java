package JPA.Book.jpashop.item.controller;

import JPA.Book.jpashop.item.domain.Item;
import JPA.Book.jpashop.item.service.ItemService;
import JPA.Book.jpashop.item.subItems.Book;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class updateItemTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private ItemService itemService;

    @Test
    @DisplayName("Dirty Checking Test")
    void updateTest() {
        // given
        Item item = new Book("dd", "dd");
        item.setPrice(2000);
        em.persist(item);

        // when -> Setter를 통한 변경감지 테스트
        Book item1 = (Book) em.find(Item.class, 1L);
        item1.setPrice(3000);

        // then
        Item byId = itemService.findById(item1.getId());
        Assertions.assertEquals(item1.getPrice(), byId.getPrice());
        //@Transactional 중요한 이유.
        // Entity Manager 에 의해 관리되고 있는 Entity 들은 늘 변경감지의 대상이 된다.
        // 이후 update 된 사항들에 대해서 DB 와의 통신 과정에서 트랜잭션 환경에서,
        // JPA에 의해서 변경 감지가 이뤄진 후, 변경사항에 맞는 Query 생성 및 Commit을 하여 DB 에 flush 하게 된다.
        // -> 이 DB와의 커넥션 및 Query 생성(JPA), commit을 프록시 패턴으로 지원하는 것이
        //    @Transactional 이다.
        // Dirty Checking이 이뤄났을 경우 동기화되는 데이터는 영속성 컨텍스트에 1차 캐싱된 Entity 이며,
        // db와의 트랙잭션이 종료된 이후에 DB - 영속성 컨텍스트 - Entity 와의 데이터가 동기화된다.
    }

}