package JPA.Book.jpashop.order.domain;


import JPA.Book.jpashop.Member.domain.Member;
import JPA.Book.jpashop.delivery.domain.Delivery;
import JPA.Book.jpashop.item.domain.subItems.OrderStatus;
import JPA.Book.jpashop.orderItem.domain.OrderItem;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="orders")
public class Order {

    @Id @GeneratedValue
    @Column(name="orders_id")
    private Long id;
    @Column(name="orders_date", updatable = false )
    private LocalDateTime orderDate;
    @Column(name="orders_update")
    @CreatedDate //Java Spring에서 제공하는 라이브러리
    private LocalDateTime orderUpdate;
    @Column(name="orders_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // 객체 세상에서는 다의 관계에 있는 Entity가 연관관계의 주인.
                                    // @JoinColumn 관계의 주인을 지정하는 컬럼이다.
    private Member member;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>( );

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="delivery_id")
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
        orderItems.add(orderItem);
        orderItem.setOrder(this); //List 컬렉션은 List 자체를 담거나, 각각의 객체를 담기도 한다.
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //양방향일 경우, 자주 조회되는 곳에서 편의 메소드를 작성하면 좋다.
}

