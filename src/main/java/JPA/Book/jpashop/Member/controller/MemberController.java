package JPA.Book.jpashop.Member.controller;


import JPA.Book.jpashop.Member.domain.Member;
import JPA.Book.jpashop.Member.dto.MemberDto;
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
            if(bindingResult.hasFieldErrors("name")){
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
        //model.addAttribute("memberId", signup);

        return "redirect:/";
    }

}
