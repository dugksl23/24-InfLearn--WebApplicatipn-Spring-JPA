package JPA.Book.jpashop.Member.dto;


import JPA.Book.jpashop.Member.domain.Member;
import JPA.Book.jpashop.item.adress.domain.Address;
import JPA.Book.jpashop.order.domain.Order;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MemberDto {

    @NotEmpty(message = "회원 이름은 필수입니다.")
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
