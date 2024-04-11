package JPA.Book.jpashop.Member.dto;


import JPA.Book.jpashop.Member.domain.Member;
import JPA.Book.jpashop.item.domain.subItems.Address;
import JPA.Book.jpashop.order.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MemberDto {

    private Long id;
    private String name;
    private Address address;
    private List<Order> orders = new ArrayList<>();

    public Member toEntity() {
        return Member.builder()
                .name(this.name)
                .address(this.address)
                .orders(this.orders)
                .build();
    }

}
