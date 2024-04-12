package JPA.Book.jpashop.order.service;

import JPA.Book.jpashop.Member.domain.Member;
import JPA.Book.jpashop.Member.repository.MemberRepository;
import JPA.Book.jpashop.item.adress.domain.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class OrderServiceTest {


    @Autowired
    private OrderService orderService;
    @Autowired
    private MemberRepository memberRepository;


    @Test
    @DisplayName("상품 주ㅜㄴ")
    void order() {
        //given
        // === 멤버 생성 ===
        Address address = new Address("a", "b", "c");
        Member.builder()
                .name("Yohan")
                .address(address)
                .build();

        // === 주문 아이템 생성 ===
//        Item build = Book.builder().title("Who moved my cheese?").author("Yohan").isbn("1234").build();
//        Item build1 = build.builder().stockQuantity(5).price(20000).name("jpa").build();


        //when

        //then


    }

    @Test
    @DisplayName("상품 취소")
    void cancelOrder() {
        //given

        //when

        //then

    }

    @Test
    @DisplayName("재고 수량 초과")
    void checkQuantiy() {
        //given

        //when

        //then

    }

    @Test
    void findAll() {
    }
}