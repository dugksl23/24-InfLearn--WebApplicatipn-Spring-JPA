package JPA.Book.jpashop.api.order.repository;

import JPA.Book.jpashop.api.order.dto.ApiOrderQueryDto;
import JPA.Book.jpashop.order.domain.Order;
import JPA.Book.jpashop.order.domain.OrderSearch;
import JPA.Book.jpashop.order.query.OrderQueryDto;
import JPA.Book.jpashop.orderItem.query.OrderItemQueryDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class OrderQueryRepository {

    private final EntityManager em;

    public List<ApiOrderQueryDto> findAllOrderDto(OrderSearch orderSearch) {
        String query = "select new JPA.Book.jpashop.api.order.dto." +
                "ApiOrderQueryDto(o.id, m.name, o.orderDate, o.delivery.deliveryAddress, o.orderStatus) from Order o" +
                " join o.member m" +
                " join o.delivery d";
        return em.createQuery(query, ApiOrderQueryDto.class).getResultList();
        // 1.JPA의 반환 타입은 Entity나 임베디드 타입만을 식별하여 오브젝트 매핑을 한다.
        // 2. select 절의 반환 타입을 Dto에 지정하기 위해서, new 명령어를 통해 값을 바인딩.
        // 3. new 문법을 통해 생성자 주입을 통한 객체로 만들 때는, 모든 매개변수를 입력해줘야 한다.
        //  ex) new JPA.Book.jpashop.api.order.dto.
        //      ApiOrderQueryDto(o.id, m.name, o.orderDate,
        //          o.delivery.deliveryAddress, o.orderStatus)
        //

    }

    public List<OrderQueryDto> findOrderQueryDtoList() {

        // 1. orderList를 1:1 관계인 member와 delivery를 별도로 뽑고, -> 2개의 오더가 존재
        List<OrderQueryDto> allOrderQueryDto = findAllOrderQueryDto();

        // 2. orderItem 은 다:1 이기에 n+1 걱정없이 조회 가능하다.
        //    따라서 별도 조회 메서드를 통해 별도 반환받아서 set한다. -> 각각의 주문별로 2개의 아이템을 주문
        // 3. item 은 orderItem을 조회할 때 join을 걸어서 가져온다.
        //  * join은 레이지 로딩일 경우에는 query를 추가적으로 더 날리고, fech join과 in query라면 한번에 같이 불러온다.
        allOrderQueryDto.forEach(o ->
        {
            List<OrderItemQueryDto> allOrderItems = findAllOrderItemDtoList(o.getOrderId());
            o.setOrderItems(allOrderItems);
            // 3-1:N이기에, 각각의 order 당 orderItem에 대해서 list로 set 한다.
            // 3-2. 모든 order에 대한 조회이기에 order 또한 list로 반환.
        });

        // --> 총 3번의 query가 발생한다.
        return allOrderQueryDto;

    }

    public List<OrderQueryDto> findAllOrderQueryDto() {
        return em.createQuery("select new JPA.Book.jpashop.order.query.OrderQueryDto" +
                "(o.id, m.name, o.orderDate, o.orderStatus, o.delivery.deliveryAddress) from Order o" +
                // JPQL의 new 명령어로 dto로 변환하더라도, collection을 바로 넣을 수 없다.
                // why? SQL처럼 데이터를 flat 하게 한줄밖에 넣는 것이다.
                // 임베디드값 타임은 컬렉션이 아니기에 가능하지만, orderItems는 collection이기에 불가능.
                " join o.member m" +
                " join o.delivery d", OrderQueryDto.class).getResultList();
    }


    // order와 연관이 있는 orderItem 조회
    private List<OrderItemQueryDto> findAllOrderItemDtoList(Long orderId) {
        return em.createQuery("select new JPA.Book.jpashop.orderItem.query.OrderItemQueryDto(oi.order.id, i.name, oi.price, oi.count) from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }


    public List<Order> findAllOrder() {
        return em.createQuery("select o from Order o", Order.class).getResultList();
    }

}