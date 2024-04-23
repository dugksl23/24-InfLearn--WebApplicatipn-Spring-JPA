package JPA.Book.jpashop.Member.services;

import JPA.Book.jpashop.Member.domain.Member;
import JPA.Book.jpashop.Member.dto.MemberDto;
import JPA.Book.jpashop.Member.repository.MemberRepository;
import JPA.Book.jpashop.item.adress.domain.Address;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("회원 가입")
    //@Rollback(false) //스프링의 @Transactional의 디폴트는 roll back
                     // JPA는 flush를 하지 않는다.
    void signup() throws Exception {

        //given
        Address address = new Address("서울","광진구","어린이대공원");
        Member yohan = MemberDto.builder()
                .address(address)
                .name("yohan")
                .build()
                .toEntity();
        //when
        memberService.validateDuplicateMember(yohan.getName());
        Long signup = memberService.signup(yohan);

        //then
        Assertions.assertThat(signup).isEqualTo(memberService.findMemberById(signup).getId());
    }

    @Test
    @DisplayName("중복회원 찾기 ")
    void validateDuplicateMember() throws Exception {

        //given
        this.signup();
        Member yohan = MemberDto.builder().name("yohan").build().toEntity();

        //when
        try{
            memberService.validateDuplicateMember(yohan.getName());
        } catch(IllegalStateException e){
            e.printStackTrace();
            //System.out.println("error msg :" + e.getStackTrace());
            return;
        }
        //then
        //Assertions.fail(memberService.validateDuplicateMember(yohan));

    }

    @Test
    void findAllMember() {
    }

    @Test
    void findMemberById() {
    }
}