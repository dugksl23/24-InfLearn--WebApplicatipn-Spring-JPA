package JPA.Book.jpashop;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public long save(Member member) {
        em.persist(member);
        return member.getId();
    }
    // Member 자체를 반환하지 않고, Long으로 된 ID만 반환하는 이유?
    // Command와 sql은 분리하라는 원칙에 따라서
    // Return 값은 되도록이면 simple 하게 고유 id로 제한한다.
    // id가 memory 기반 위에 있으면 검색이 유연해지고 빨라진다.


    public Member findById(long id) {
        return em.find(Member.class, id);
    }


}
