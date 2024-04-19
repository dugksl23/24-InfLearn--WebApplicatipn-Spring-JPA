package JPA.Book.jpashop.api.member;

import JPA.Book.jpashop.item.adress.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiMemberResponse {

    private Long id;
    private String memberName;
    private Address address;

    public static ApiMemberResponse createMemberResponse(Long memberId, String memberName, Address address) {
        return ApiMemberResponse.builder().id(memberId).memberName(memberName).address(address).build();
    }

}
