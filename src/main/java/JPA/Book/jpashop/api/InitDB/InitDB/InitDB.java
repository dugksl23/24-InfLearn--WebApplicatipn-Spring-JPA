package JPA.Book.jpashop.api.InitDB.InitDB;

import JPA.Book.jpashop.Member.domain.Member;
import JPA.Book.jpashop.delivery.domain.Delivery;
import JPA.Book.jpashop.delivery.domain.DeliveryStatus;
import JPA.Book.jpashop.item.adress.domain.Address;
import JPA.Book.jpashop.item.subItems.Book;
import JPA.Book.jpashop.order.domain.Order;
import JPA.Book.jpashop.orderItem.domain.OrderItem;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * 총 2개 주문 2개
 * userA
 * JPA1 BOOK
 * JPA2 BOOK
 * <p>
 * userB
 * SPRING1 BOOK
 * SPRING2 BOOK
 */
@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct // 생성자 생성 이후에 호출된다.
    @Transactional
    public void init() {
        initService.dbInit();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;


        public void dbInit() {

            Address address = new Address("a", "b", "c");
            Member userA = getMember(address);
            em.persist(userA);

            Book book = getBook("JPA Book1");
            Book book2 = getBook("JPA Book2");
            em.persist(book);
            em.persist(book2);

            OrderItem orderItem = OrderItem.createOrderItem(book, book.getPrice(), 1);//각각의 order의 총 금액
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, book2.getPrice(), 3);

            Delivery delivery = Delivery.builder().deliveryAddress(address).status(DeliveryStatus.READY).build();
            Order order = Order.createOrder(userA, delivery, orderItem, orderItem2);
            /**
             *  OrderItem... orderItems
             *  OrderItem 타입의 가변인자를 나타냅니다.
             *  이 메소드를 호출할 때는 다음과 같이 여러 개의 OrderItem 인스턴스를 전달할 수 있습니다.
             */
            em.persist(order);

            // === @PostConstruct　가 붙은 메서드에서 위의 로직은 처리 하지 않는 이유?
            //     스프링부트의 라이프 사이클로 인해서 db 커넥션을 위해 @Transactional 을 사용해야 하는데
            //     라이프사이클 충돌로 인해서 충돌이 일어나서 진행되지 않기에, 별도 컴포넌트 등록후에 트랙잭션처리를 진행!

        }


        public void dbInit2() {

            Address address = new Address("a", "b", "c");
            Member userA = Member.builder().name("UserB").address(address).build();
            em.persist(userA);

            Book book = getBook("SPRING Book1");
            em.persist(book);

            Book book2 = getBook("SPRING Book2");
            em.persist(book2);

            OrderItem orderItem = OrderItem.createOrderItem(book, book.getPrice(), 1);//각각의 order의 총 금액
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, book2.getPrice(), 3);

            Delivery delivery = Delivery.builder().deliveryAddress(address).status(DeliveryStatus.READY).build();
            Order order = Order.createOrder(userA, delivery, orderItem, orderItem2);
            /**
             *  OrderItem... orderItems
             *  OrderItem 타입의 가변인자를 나타냅니다.
             *  이 메소드를 호출할 때는 다음과 같이 여러 개의 OrderItem 인스턴스를 전달할 수 있습니다.
             */
            em.persist(order);

            // === @PostConstruct　가 붙은 메서드에서 위의 로직은 처리 하지 않는 이유?
            //     스프링부트의 라이프 사이클로 인해서 db 커넥션을 위해 @Transactional 을 사용해야 하는데
            //     라이프사이클 충돌로 인해서 충돌이 일어나서 진행되지 않기에, 별도 컴포넌트 등록후에 트랙잭션처리를 진행!

        }
    }

    private static Book getBook(String JPA_Book1) {
        Book book = new Book();
        book.setAuthor("dd");
        book.setIsbn("dd");
        book.setName(JPA_Book1);
        book.setPrice(20000);
        book.setStockQuantity(100);
        return book;
    }

    private static Member getMember(Address address) {
        Member userA = Member.builder().name("UserA").address(address).build();
        return userA;
    }


}
