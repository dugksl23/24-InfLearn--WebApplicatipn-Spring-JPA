package JPA.Book.jpashop.Entity;


import JPA.Book.jpashop.domain.Address;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Delivery {


    @Id @GeneratedValue
    @Column(name="delivery_id")
    private Long id;

    @Embedded
    @Column(name="delivery_address")
    private Address deliveryAddress;

    @Column(name="delivery_status")
    @Enumerated(EnumType.STRING)
    //Enum type은 반드시 @Enumurated 어노테이션 추가 및 기본 속성은 String, Ordinal은 숫자로 들어가기에 절대 쓰면 안된다.
    private DeliveryStatus status;

    @OneToOne(mappedBy = "delivery")// mappedby : 매핑되어있는 테이블의 이름을 명시. 조인을 위한 값이 아님.
    private order order;


}
