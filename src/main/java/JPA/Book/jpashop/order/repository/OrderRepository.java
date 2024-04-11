package JPA.Book.jpashop.order.repository;


import JPA.Book.jpashop.order.domain.Order;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@Transactional(readOnly = true)
public class OrderRepository {

    private final EntityManager em;

    public void saveOrder(Order order) {
        em.persist(order);
    }

    public long findOrderById(Long id) {
        Order order = em.find(Order.class, id);
        return order.getId();
    }

    public List<Order> findAllOrders() {
        return em.createQuery("select 0 from Order o", Order.class).getResultList();
    }

    public List<Order> SearchOrder (String keyword) {
        return new ArrayList<>();
    }


}
