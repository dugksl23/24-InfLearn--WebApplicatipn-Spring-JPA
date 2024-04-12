package JPA.Book.jpashop.order.service;


import JPA.Book.jpashop.Member.domain.Member;
import JPA.Book.jpashop.Member.repository.MemberRepository;
import JPA.Book.jpashop.delivery.domain.Delivery;
import JPA.Book.jpashop.item.domain.Item;
import JPA.Book.jpashop.item.repository.ItemRepository;
import JPA.Book.jpashop.order.domain.Order;
import JPA.Book.jpashop.order.repository.OrderRepository;
import JPA.Book.jpashop.orderItem.domain.OrderItem;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final EntityManager em;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // Member 조회
        Member member = memberRepository.findMemberById(memberId);
        // 아이템 조회
        Item item = itemRepository.getItemById(itemId);
        //배송 정보 조회
        Delivery delivery = Delivery.builder().deliveryAddress(member.getAddress()).build();

        // === 1. 주문 상품 생성 ===
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // === 2. 주문을 생성 ===
        Order order = Order.createOrder(member, delivery, orderItem);

        // === 3. 주문을 저장  ==
        Long orderId = orderRepository.saveOrder(order);
        //Order의 Cascase 옵션에 의해 연관 관계에 있는 모든 Entity의 데이터가 영속화된다.

        // === 4. cascade의 범위 ===
        // 해당 필드값들을 Private owner만 embadded 값으로만 참조된다면 cascade 사용을 권장.
        // 즉 라이프사이클이 private Owner(order)에 국한된다면 사용가능.
        Order orderById = orderRepository.findOrderById(orderId);
        return orderId ;
    }

    //취소
    public void cancelOrder(Long orderId) {
        Order orderById = orderRepository.findOrderById(orderId);
        Delivery delivery = orderById.getDelivery();
        orderById.cancelOrder(orderById.getMember(), orderById.getDelivery() );

        //Member member, Delivery delivery, OrderStatus orderStatus) {
    }


    //주문 조회
    public Order searchOrder(String keyward) {
        //em.find();
        return null;
    }

    //주문 전체 조회
    public List<Order> findAll() {
        return em.createQuery("select o from Order o", Order.class).getResultList();
    }


}
