package JPA.Book.jpashop.Member.services;


import JPA.Book.jpashop.Member.domain.Member;
import JPA.Book.jpashop.Member.repository.MemberRepository;
import JPA.Book.jpashop.api.member.ApiMemberResponse;
import JPA.Book.jpashop.item.adress.domain.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //조회 성능 최적화
@RequiredArgsConstructor //3.final이 있는 필드로만 생성자를 생성.
public class MemberService {
    // 1. final　전역 변수로 스프링 빌드시 최초 1회 초기화를 진행하며 등록이 된다.
    // 이후에는 값이 변경될 일이 없기에 final 어노테이션 사용 권장.

    private final MemberRepository memberRepository;

    //2. 최신 스프링에서는 생성자가 하나일 경우에는, 자동 AutoWired 인젝션 진행
    //public MemberService(MemberRepository memberRepository) {
    //this.memberRepository = memberRepository;
    // 생성자를 통해 객체화 하지 않을 경우에는 컴파일 시점에 체크가 된다.

// 생성자 주입을 통한 스프링 빈 등록 및 MockTest 가능

    /*
     *회원가입
     */
    @Transactional //별도 메소드 단위의 설정된 어노테이션을 우선시한다.
    public Long signup(Member member) {
        memberRepository.saveMember(member);
        return member.getId();

    }

    public void validateDuplicateMember(Member member) {
        List<Member> memberByName = memberRepository.findMemberByName(member.getName());//id 검사
        if (!memberByName.isEmpty()) {
            throw new IllegalStateException("존재하는 회원 ID 입니다. " + memberByName);
        }

    }

    //회원전체 조회
    public List<Member> findAllMember() {
        return memberRepository.findAllMembers();
    }

    public Member findMemberById(Long id) {
        return memberRepository.findMemberById(id);
    }

    @Transactional
    public ApiMemberResponse update(Long memberId, String memberName, Address address) {
        Member memberById = memberRepository.findMemberById(memberId);
        memberById.updateMember(memberName, address);
        // == 커맨드와 Query의 분리 원칙으로 인해 Query를 다시 생성.
        Member updatedMember = memberRepository.findMemberById(memberId);

        return ApiMemberResponse.createMemberResponse(updatedMember.getId(), updatedMember.getName(), updatedMember.getAddress());
    }
}
