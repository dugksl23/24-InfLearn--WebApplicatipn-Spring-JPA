package JPA.Book.jpashop.api.member;


import JPA.Book.jpashop.item.adress.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ApiUpdateMemberRequest {

    private String memberName;
    private Address address;

}
