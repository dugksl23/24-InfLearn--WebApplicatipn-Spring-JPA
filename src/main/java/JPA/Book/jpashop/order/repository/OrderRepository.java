package JPA.Book.jpashop.order.repository;

import JPA.Book.jpashop.Member.domain.QMember;
import JPA.Book.jpashop.order.domain.Order;
import JPA.Book.jpashop.order.domain.OrderSearch;
import JPA.Book.jpashop.order.domain.OrderStatus;
import JPA.Book.jpashop.order.domain.QOrder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderRepository {

    private final EntityManager em;
    private JPAQueryFactory query;

    public Long saveOrder(Order order) {
        em.persist(order);
        return order.getId();
    }

    public Order findOrderById(Long id) {
        Order order = em.find(Order.class, id);
        return order;
    }

    public List<Order> findAllOrders(OrderSearch orderSearch) {

        QOrder order = QOrder.order;
        QMember member = QMember.member;

        return query
                .select(order)
                .from(order)
                .join(order.member, member)
                .where(statusEq(orderSearch.getOrderStatus()),
                        nameLike(orderSearch.getMemberName()))
                .limit(1000)
                .fetch();

    }
    // JPQL Join 문 ex)　select o from Order o left join Member m

    public List<Order> SearchOrder(String keyword) {
        return new ArrayList<>();
    }

    private BooleanExpression statusEq(OrderStatus statusCond){
        QOrder order = QOrder.order;

        if (statusCond == null){
            return null;
        }
        return order.orderStatus.eq(statusCond);
    }

    private BooleanExpression nameLike(String nameCond){
        QMember member = QMember.member;
        if (!StringUtils.hasText(nameCond)){
            return null;
        }
        return member.name.like(nameCond);
    }


}
