package JPA.Book.jpashop.Member.repository;


import JPA.Book.jpashop.Member.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 컴포넌트 스캔에 의해 자동으로 스프링 빈으로 등록됨
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    @PersistenceUnit
    private EntityManagerFactory emf;
    //스프링 빈에 자동 등록이 아닌, 내가 직접 EntityManagerFactory를 주입하고 싶다면,
    //@PersistanceUnit 를 통해 직접 주입 가능.


    private void saveMember(Member member) {
        em.persist(member);
        //EntityManager에 의해 영속성 컨텍스트에 persist(영속화)가 된다.
        //이후 db와의 transaction 과정에서 db에 보존 저장된다.
    }

    private Member findMemberById(Long memberId) {
        return em.find(Member.class, memberId);
        //단건 조회
    }

    private List<Member> findAllMembers() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
        //JPQL은 Entity를 대상으로 Query하기 위한 언어이며, 이후 sql로 변환하여 db에 트랜잭션 커밋을 진행한다.
    }

    private List<Member> findMemberByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name).getResultList();
    } // :name 을 jpql 문법을 통해서 String 값을 파라미터 바인딩한다.
    // 스프링은 @SpringBootApplication가 등록된 패키지 및 그 하위에 있는
    // 모든 어노테이션을 대상으로 컴포넌트 스캔을 진행한다.
    // @Component 어노테이션이 등록된 객체를 스프링 빈으로 자동 등록.



}
