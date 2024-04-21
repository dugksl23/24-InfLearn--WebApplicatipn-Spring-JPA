package JPA.Book.jpashop.order.domain;


import JPA.Book.jpashop.Member.domain.Member;
import JPA.Book.jpashop.delivery.domain.Delivery;
import JPA.Book.jpashop.delivery.domain.DeliveryStatus;
import JPA.Book.jpashop.orderItem.domain.OrderItem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;
    @Column(name = "order_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime orderDate;
    @Column(name = "order_update")
//    @CreatedDate //Java Spring에서 제공하는 라이브러리
    @UpdateTimestamp
    private LocalDateTime orderUpdateDate;
    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id") // 객체 세상에서는 다의 관계에 있는 Entity가 연관관계의 주인.
    //관계의 주인을 지정하는 컬럼이다.
    private Member member;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    //하나의 주문은 하나의 배송정보를 갖기에 둘다 1:1 관계이다.
    //이때는 조회를 많이 하는 곳에서 관계의 주인을 둔다.
    private Delivery delivery;

    // == Member와 Order 연관 관계 메서드 ==
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this); //연관관계의 주인이기에 update 가능
    }

    // service area에서 별도 비지니스 로직을 구현했을 경우 ==
//    public void updateMember(Member member) {
//        Member member1 = new Member();
//        Order order = new Order();
//
//        member.getOrders().add(order);
//    }


    public void addOrderItem(OrderItem orderItem) {
        //한개만 주문했을 때의 비지니스 로직
//        if (orderItems == null) {
//            orderItems = new ArrayList<>();
//        }
        orderItems.add(orderItem);
        orderItem.setOrder(this); //List 컬렉션은 List 자체를 담거나, 각각의 객체를 담기도 한다.
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    /**
     *  OrderItem... orderItems
     *  OrderItem 타입의 가변인자를 나타냅니다.
     *  이 메소드를 호출할 때는 다음과 같이 여러 개의 OrderItem 인스턴스를 전달할 수 있습니다.
     */
    // == 생성 메서드 ==
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = Order.builder()
                .member(member)
                .delivery(delivery)
                .orderStatus(OrderStatus.ORDER)
                .orderItems(new ArrayList<>())
                .build();
        //orderItems.stream().forEach(orderItem -> order.addOrderItem(orderItem));
        Arrays.stream(orderItems).forEach(orderItem -> order.addOrderItem(orderItem));

        return order;

    }


    /*
     * === 주문 취소 비지니스 로직 ===
     * */
    public void cancelOrder(Member member, Delivery delivery) {

        if (delivery.getStatus().equals(DeliveryStatus.COMP)) throw new IllegalStateException("이미 배송 중인 취소가 불가합니다.");
        else this.orderStatus = OrderStatus.CANCEL;
        orderItems.stream().forEach(orderItem -> orderItem.cancel()); //수량 원복
        // cancel()로 인해서 변경 감지가 일어나서 자동으로 jpa가 변경사항을 추적 및 반영한다.

    }

    /* === 전체 주문 가격 조회 === */
    public int getTotalPrice(){
        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }



}

