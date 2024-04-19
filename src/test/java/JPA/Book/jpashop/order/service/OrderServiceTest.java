package JPA.Book.jpashop.order.service;

import JPA.Book.jpashop.Member.domain.Member;
import JPA.Book.jpashop.Member.services.MemberService;
import JPA.Book.jpashop.delivery.domain.DeliveryStatus;
import JPA.Book.jpashop.item.adress.domain.Address;
import JPA.Book.jpashop.item.domain.Item;
import JPA.Book.jpashop.item.service.ItemService;
import JPA.Book.jpashop.item.subItems.Album;
import JPA.Book.jpashop.item.subItems.Book;
import JPA.Book.jpashop.order.domain.Order;
import JPA.Book.jpashop.order.domain.OrderStatus;
import JPA.Book.jpashop.order.repository.OrderRepository;
import JPA.Book.jpashop.orderItem.domain.OrderItem;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class OrderServiceTest {


    @Autowired
    private OrderService orderService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("상품 주문")
    void order() {
        //given
        // === 멤버 생성 ===
        Address address = new Address("a", "b", "c");
        Member member = Member.builder()
                .name("Yohan")
                .address(address)
                .build();
        Long memberId = memberService.signup(member);

        // === 아이템 생성 ===
        /*  1. 주문할 아이템의 Info는 vieｗ에서 이미 request로 값을 바인딩한다.
         *  2. 해당 item의 수량도 함께 보낸다.
         *  3. OrderEntity의 order() 메서드에서 주문 수량을 보내준다.
         * */

        Book book = new Book();
        // 1. book에 대한 info는 이미 db에 있으며, 선택된 item의 수량/가격은 ItemEntity에 담긴다.
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        Long bookId = itemService.saveItem(book);

        Book book1 = new Book();
        book1.setName("시골 JPA11");
        book1.setPrice(10000);
        book1.setStockQuantity(10);
        Long bookId1 = itemService.saveItem(book1);

        //when
        int orderStock = 2;
        Long order = orderService.order(memberId, bookId, orderStock);
        Long order1 = orderService.order(memberId, bookId1, orderStock);
        //then
        Order one = orderService.findOne(order);
        Assertions.assertThat(one.getId()).isEqualTo(order);

        assertEquals("상품 주문 시 주문 상태는 Order ", OrderStatus.ORDER, one.getOrderStatus());
        assertEquals("상품 주문 시 배송 상태는 COM ", DeliveryStatus.READY, one.getDelivery().getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다. ", 1, one.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다. ", book.getPrice() * one.getOrderItems().get(0).getCount(), one.getOrderItems().get(0).getTotalPrice());
        assertEquals("주문 가격 List 별 sum()은? ", book.getPrice() * one.getOrderItems().get(0).getCount(), one.getOrderItems().stream().mapToInt(OrderItem::getTotalPrice).sum());
        assertEquals("주문 수량만큼 재고가 감소해야 한다. ", 8, one.getOrderItems().get(0).getItem().
                getStockQuantity());

        System.out.println();
        System.out.println("sum의 값은 {}" + one.getOrderItems().stream().mapToInt(OrderItem::getTotalPrice).sum());
    }

    //@Test(expected = ChangeSetPersister.NotFoundException.class)
    @Test
    @ExceptionHandler(NullPointerException.class)
    @DisplayName("상품 재고 수량 초과")
    void checkQuantity() {
        //given
        Address address = new Address("a", "b", "c");
        Member member = Member.builder().name("Yohan").address(address).build();
        Long memberId = memberService.signup(member);

        //when
        Album album = new Album();
        album.setArtist("Yohaness");
        album.setEtc("ddd");
        album.setPrice(10000);
        album.setStockQuantity(3);
        Long albumId = itemService.saveItem(album);

        Integer orderItemQuantity = 3;
        Long order = orderService.order(memberId, album.getId(), orderItemQuantity);

        //then
        fail("재고 수량 부족 예외가 발생해야 한다.");

    }

    @Test
    @DisplayName("주문 취소")
    @Transactional
    void cancelOrder() {
        //given
        Member member = Member.builder().name("Yohan").build();
        Long memberId = memberService.signup(member);
        Book item = new Book();
        item.setPrice(10000);
        item.setStockQuantity(10);
        item.setName("12312");
        Long bookId = itemService.saveItem(item);

        //when
        Long order = orderService.order(member.getId(), bookId, 5);
        orderService.cancelOrder(order);

        //then
        Item byId = itemService.findById(order);
        int stockQuantity = byId.getStockQuantity();
        Assertions.assertThat(stockQuantity).isEqualTo(10);

    }

}