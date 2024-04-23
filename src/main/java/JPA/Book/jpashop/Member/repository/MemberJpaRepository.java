package JPA.Book.jpashop.Member.repository;

import JPA.Book.jpashop.Member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    //select * from Member m where m.name = ??
    List<Member> findByName(String name);
}
