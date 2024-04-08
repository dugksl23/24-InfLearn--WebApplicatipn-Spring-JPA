package JPA.Book.jpashop.Entity;


import JPA.Book.jpashop.domain.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
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


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id") // 객체 세상에서는 다의 관계에 있는 Entity가 연관관계의 주인.
                                    // @JoinColumn 관계의 주인을 지정하는 컬럼이다.
    // 더불어 FetchType.EAGER인 이유는 해당 오더의 주인이 누군지 명확히 알아야하기에, 첫 쿼리부터
    // Member_id를 조인할 수밖에 없다. 그렇다면 해당 데이터를 처음부터 불러오는 것이 성능적으로 유리하다.
    private Member members;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="delivery_id")
    //하나의 주문은 하나의 배송정보를 갖기에 둘다 1:1 관계이다.
    //이때는 조회를 많이 하는 곳에서 관계의 주인을 둔다.
    private Delivery delivery;

}
