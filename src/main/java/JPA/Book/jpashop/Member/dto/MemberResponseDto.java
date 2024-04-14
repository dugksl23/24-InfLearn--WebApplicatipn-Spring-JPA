package JPA.Book.jpashop.Member.dto;


import JPA.Book.jpashop.Member.domain.Member;
import JPA.Book.jpashop.item.adress.domain.Address;
import JPA.Book.jpashop.order.domain.Order;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseDto {

    private Long id;
    @NotBlank(message = "회원 이름은 필수입니다.") // null,"", " " 를 모두 허용하지 않는다.
    private String name;
    private Address address;
    private List<Order> orders = new ArrayList<>();

    public static List<MemberResponseDto> toMemberResponseDto(List<Member> allMembers) {
        List<MemberResponseDto> memberList = allMembers.stream().map(member -> {
                    MemberResponseDto response = MemberResponseDto.builder()
                            .id(member.getId())
                            .name(member.getName())
                            .address(member.getAddress())
                            .build();

                    return response;
                })
                .collect(Collectors.toList());
        return memberList;

    }

}
