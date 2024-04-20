package JPA.Book.jpashop.api.order.repository;

import JPA.Book.jpashop.api.order.dto.ApiOrderQueryDto;
import JPA.Book.jpashop.order.domain.OrderSearch;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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



}
