//package JPA.Book.jpashop;
//
//import JPA.Book.jpashop.Entity.Member;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//class memberRepositoryTest {
//
//    @Autowired
//    //@Autowired를 통해 MemberRepository 인젝션 받음
//    MemberRepository memberRepository;
//
//    @Test
//    @Transactional //Entity Manager를 통한 모든 데이터 변경은 반드시 Transaction 안에서 이루어져야 한다.
//    @Rollback(value = false )                                    //더불어 Test case에서는 Transactional은 rollback을 기본으로 한다.
//    public void save() throws Exception {
//        //given
//        Member member = new Member();
//        member.setUserName("dugksl23");
//        //when
//        long saveId = memberRepository.save(member);
//        Member byId = memberRepository.findById(saveId);
//        //then
//
//        Assertions.assertThat(byId.getId()).isEqualTo(saveId);
//        Assertions.assertThat(byId.getUserName()).isEqualTo(member.getUserName());
//
//        Assertions.assertThat(member).isEqualTo(byId);
//        //view에서 넘어온 객체값과 영속성 컨텍스트에 동기화되어 조회된 member가 동일한지를 확인하기 위함.
//        //결과는 true,
//        //why? 동일한 Transaction 아래에서 영속성 컨텍스트는 같은 값을 가지기에, 식별자가 같으면 1차 캐싱된 이후의 엔티티 값이 반환되기에
//        //동일 엔티티로 취급된다.
//
//
//    }
//
//    @Test
//    @Transactional
//    void findById() {
//    }
//}