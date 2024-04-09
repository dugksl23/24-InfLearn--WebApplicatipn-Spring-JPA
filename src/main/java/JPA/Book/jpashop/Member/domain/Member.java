package JPA.Book.jpashop.Member.domain;

import JPA.Book.jpashop.item.domain.subItems.Address;
import JPA.Book.jpashop.order.domain.Order;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {


    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;
    @Column(name="memberName")
    private String name;
    @Embedded //임베디드 타입, 내장 타입(종속변수)로 활용하는 Entity에서 @Embedded 어노테이션을 활용하면,
              // 해당 class에서는 @Embeddable 을 하지 않아도 되지만, 일반적으로 둘다 활용.
    private Address address;

    //DB입장에서는 Order_id를 외부키로 매핑한다.
    //더불어, 멤버의 데이터를 변경할 때는 멤버만 조회한다.
    // 따라서 Orders의 데이터는 필요한 시기 때 불러오게 하면 된다.
    // 더불어 orders를 불러올 때는, Orders라는 테이블에서 직접 조회를 하여, Member_id를 조인하기에
    // Orders의 특정 값을 변경할 때는 Orders를 통해서 진행한다.

    //====== MappedBy 사용 ========
    // 연관관계의 주인이 아닐 경우 mappedBy 사용
    // orders 테이블에 있는 member 필드에 의해 연관관계 매핑
    //여기서 값을 설정해도 fk 값이 변경되지 않음
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();


    //1. db에서는 기본키와 외래키로 나눈다. 관계의 주인(보통 다의 관계에 해당하는 Entity)이 해당 테이블에 기본키와 외래키를 갖는다. 즉 연관관계의 주인이 된다.
    //2. 객체 세상에서는 외래키로 매핑된 다의 입장을 연관관계의 주인으로 본다.




}
