package JPA.Book.jpashop.Member.services;

import JPA.Book.jpashop.Member.domain.Member;
import JPA.Book.jpashop.Member.repository.MemberJpaRepository;
import JPA.Book.jpashop.api.member.ApiMemberResponse;
import JPA.Book.jpashop.item.adress.domain.Address;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@ToString
public class MemberJpaService {

    private final MemberJpaRepository memberRepository;

    /*
     *회원가입
     */
    @Transactional //별도 메소드 단위의 설정된 어노테이션을 우선시한다.
    public Long signup(Member member) {
        Member save = memberRepository.save(member);
        return save.getId();

    }

    public void validateDuplicateMember(Member member) {
        List<Member> memberByName = memberRepository.findByName(member.getName());//id 검사
        if (!memberByName.isEmpty()) {
            throw new IllegalStateException("존재하는 회원 ID 입니다. " + memberByName);
        }

    }

    //회원전체 조회
    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }

    public Member findMemberById(Long id) {
        Optional<Member> byId = memberRepository.findById(id);
        return byId.orElse(null);
    }

    @Transactional
    public ApiMemberResponse update(Long memberId, String memberName, Address address) {
        Member memberById = memberRepository.findById(memberId).get();
        memberById.updateMember(memberName, address);
        // == 커맨드와 Query의 분리 원칙으로 인해 Query를 다시 생성.
        Optional<Member> updateMember = memberRepository.findById(memberId);

        return ApiMemberResponse.createMemberResponse(
                updateMember.get().getId(), updateMember.get().getName(), updateMember.get().getAddress());
    }

}
