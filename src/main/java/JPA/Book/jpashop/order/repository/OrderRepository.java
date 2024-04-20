package JPA.Book.jpashop.order.repository;

import JPA.Book.jpashop.order.domain.Order;
import JPA.Book.jpashop.order.domain.OrderSearch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderRepository {

    private final EntityManager em;

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

        String jpql = "select o From Order o left join o.member m";
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

}
