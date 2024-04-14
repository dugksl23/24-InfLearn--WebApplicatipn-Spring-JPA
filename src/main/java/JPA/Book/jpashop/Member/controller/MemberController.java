package JPA.Book.jpashop.Member.controller;


import JPA.Book.jpashop.Member.domain.Member;
import JPA.Book.jpashop.Member.dto.MemberDto;
import JPA.Book.jpashop.Member.dto.MemberResponseDto;
import JPA.Book.jpashop.Member.services.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/signup")
    public String signup(Model model, MemberDto memberDto) {
        log.info("signup get으로 들어옴");
        model.addAttribute("memberDto", new MemberDto());
        // Model은, controller에서 view에 data를 전달하는 객체
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberDto memberDto, BindingResult bindingResult, Model model) {
        //@Valid : Java Validation 라이브러리에서 제공하는 어노테이션
        //         해당 객체에 @NotNull, @NotEmpty 가 붙은 필드를 대상으로 validation 진행
        if (bindingResult.hasErrors()) {
            // 어떤 필드를 대상으로 error가 났는지 특정하는 메서드
            if (bindingResult.hasFieldErrors("name")) {
                log.info("filed error 남");
            }
            // 바인딩 에러가 있을 때 처리하는 로직
            // 에러 페이지로 이동하거나 다른 처리를 수행할 수 있음
            return "/member/signup";
        }


        Member entity = MemberDto.builder()
                .name(memberDto.getName())
                .address(memberDto.getAddress())
                .build().toEntity();

        Long signup = memberService.signup(entity);

        return "redirect:/";
    }

    @GetMapping("/memberList")
    public String memberList(Model model) {
        List<Member> allMember = memberService.findAllMember();
        List<MemberResponseDto> memberList = allMember.stream().map(member -> {
                    MemberResponseDto response = MemberResponseDto.builder()
                            .id(member.getId())
                            .name(member.getName())
                            .address(member.getAddress())
                            .build();

                    return response;
                })
                .collect(Collectors.toList());

        model.addAttribute("memberList", memberList);
        //response 객체는 되도록 dto를 통해 데이터만 넘기는 것이 바람직하다.
        //Entity는 최대한 비지니스 로직에만 종속되도록 기능을 최적화 해야 한다.
        // 특히, restAPI를 통해 data를 반환한 경우에는 반드시 dto로 담아서 보내야 한다.
        //현재 thymeleaf는 서버 사이드 렌드링을 통해 값을 찍고 html을 읽기에 문제가 덜하다.
        //하지만 view와 server side의 렌더링이 별도 이루어지며, api를 통해 데이터를 주고 받을 경우에는
        // 반드시 dto를 통해 반환해야 한다!!
        return "/member/memberList";
    }


}
