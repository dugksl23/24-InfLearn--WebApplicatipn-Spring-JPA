package JPA.Book.jpashop.order.repository;

import JPA.Book.jpashop.Member.domain.QMember;
import JPA.Book.jpashop.api.order.dto.ApiOrderDto;
import JPA.Book.jpashop.order.domain.Order;
import JPA.Book.jpashop.order.domain.OrderSearch;
import JPA.Book.jpashop.order.domain.OrderStatus;
import JPA.Book.jpashop.order.domain.QOrder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class OrderRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public OrderRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Long saveOrder(Order order) {
        em.persist(order);
        return order.getId();
    }

    public Order findOrderById(Long id) {
        Order order = em.find(Order.class, id);
        return order;
    }

    //주문 상태 검색
    public List<Order> findAllOrders(OrderSearch orderSearch) {

        String jpql = "select o From Order o";
//                + " left join o.member m";

        boolean isFirstCondition = true;
        // JPQL Join 문 ex)　select o from Order o left join Member m

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.orderStatus = :orderStatus";
        }

        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000);

        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("orderStatus", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();

    }

    public List<Order> findAllOrdersFetchJoinMemberDelivery(OrderSearch orderSearch) {
        String query = "select o from Order o" +
                " join fetch o.member m" +
                " join fetch o.delivery d";
        return em.createQuery(query, Order.class).getResultList();
        //fetch Join은 한 번의 Query로 memberGraph의 객체 값을 불러온다. => inner join.
    }


    public List<Order> findAllOrderWithItem(OrderSearch orderSearch) {
        return em.createQuery("select distinct o from Order o " +
                        " join fetch o.member m" +
                        " join fetch o.delivery d" +
                        " join fetch o.orderItems oi" +
                        " join fetch oi.item i", Order.class)
                .getResultList();
        // JPA Distinct : 중복이 되는 식별자를 대상으로 중복을 없애는 명령어이며, DB에서는 중복이 되는 record는 없애는 db 명령어도 한다.
        // 결과는? distinct　와 무관하게 collection 에는 order는 size가 2개이다.
    }

    public List<ApiOrderDto> findAllOrderFetchMDWithPaging(int offset, int limit) {
        return em.createQuery("select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList()
                .stream().map(ApiOrderDto::new).toList();
    }


    public List<Order> findAll(OrderSearch orderSear) {

        QOrder order = QOrder.order;
        QMember member = QMember.member;

        return query.select(order)
                .from(order)
                .join(order.member, member)//여기서의 member는 QMember의 member를 alias로 지정하겠다는 것.
                .where(statusEq(orderSear.getOrderStatus()), memberNameLike(orderSear.getMemberName()))
                        // if 문을 통한 동적 query를 위해서 statusEq()함수를 활용.
                        .limit(1000)
                        .fetch();
    }

    // queryDSL type을 import
    private BooleanExpression statusEq(OrderStatus statusCondition) {
        if (statusCondition == null) {
            return null;
        }
        return QOrder.order.orderStatus.eq(statusCondition);
    }

    // queryDSL type을 import
    private BooleanExpression memberNameLike(String memberName) {
        if (!StringUtils.hasText(memberName)) {
            return null;
        }

        return QMember.member.name.like(memberName);
    }

}
