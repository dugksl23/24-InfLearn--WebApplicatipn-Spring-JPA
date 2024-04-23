package JPA.Book.jpashop.order.repository;

import JPA.Book.jpashop.Member.domain.QMember;
import JPA.Book.jpashop.order.domain.Order;
import JPA.Book.jpashop.order.domain.OrderSearch;
import JPA.Book.jpashop.order.domain.QOrder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface OrderJpaRepository extends JpaRepository<Order, Long> {

}
