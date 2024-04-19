package JPA.Book.jpashop.api;


import JPA.Book.jpashop.Member.domain.Member;
import JPA.Book.jpashop.Member.services.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
/*
 * @Controller, @ResponseBody
 * -> 이 두가지를 합친 것이 바로 @RestController
 * */
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Slf4j
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/v1/signup")
    public ApiMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        //@RequestBody 는 JsonBody 에 담긴 data 를 Member 객체에 바인딩하는 어노테이션
        Long memberId = memberService.signup(member);
        return ApiMemberResponse.createMemberResponse(memberId, member.getName(), member.getAddress());
    }

    @GetMapping("/v1/memberList")
    public List<Member> getMemberListV1() {
        List<Member> allMember = memberService.findAllMember();
        return allMember;
    }

    @GetMapping("/v2/memberList")
    public ApiResponseResult getMemberListV2() {
        List<MemberDto> findMembers = new ArrayList<>();
        memberService.findAllMember().stream()
                .map(member -> findMembers.add(new MemberDto(member.getName())))
                .collect(Collectors.toList());
        long count = findMembers.stream().count();
        int size = findMembers.size();
        return new ApiResponseResult(count, findMembers);
    }


    @PostMapping("/v2/signup")
    public ApiMemberResponse saveMemberV2(@RequestBody @Valid ApiMemberRequest request) {

        Long memberId = memberService.signup(request.createApiMemberRequest());
        return ApiMemberResponse.createMemberResponse(memberId, request.getMemberName(), request.getAddress());
    }

    @PutMapping("/v2/update/{memberId}")
    public ApiMemberResponse updateMemberV2(
            @PathVariable("memberId") Long memberId,
            @RequestBody @Valid ApiUpdateMemberRequest request) {

        return memberService.update(memberId, request.getMemberName(), request.getAddress());
    }

}
