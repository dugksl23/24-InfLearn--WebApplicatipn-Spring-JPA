package JPA.Book.jpashop.api.member;

import JPA.Book.jpashop.Member.domain.Member;
import JPA.Book.jpashop.item.adress.domain.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiMemberRequest {

    @NotBlank(message = "입력해주세요.")
    //apiMemberRequestDto를 통해서 요청에 따른 유효성 검사를 하고, entity에서는 해당 역할을 분리한다.
    //그리고 dto를 통해서 api(presentation 계층)와 entity의 스펙을 명확히 분리할 수 있다.
    //web과 presentation 계층에서 요구되는 역할 ex) 유효성 검사 등등.. 과 같은 역할을 분리할 수 있으며,
    //entity의 스펙이 변경되더라도 api의 스펙은 변경되지 않는다.
    //실무에서는 web 계층과 presentation(api) 계층에 entity를 노출해선 안되고, entity로 값을 바인딩해서도 안된다.
    // -> api와 web 계층에서의 요구 및 응답은 모두 dto를 통해 진행한다.
    private String memberName;
    private Address address;

    public Member createApiMemberRequest() {
        Member build = Member.builder().name(this.memberName).address(address).build();
        return build;
    }

}
