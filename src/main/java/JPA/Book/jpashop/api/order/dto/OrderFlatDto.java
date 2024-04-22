package JPA.Book.jpashop.api.order.dto;


import JPA.Book.jpashop.item.adress.domain.Address;
import JPA.Book.jpashop.order.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderFlatDto {
    // select 절에 모든 데이터를 flat하게 조인해서 1번 query로 가져오는 것.

    private Long orderId;

    public OrderFlatDto(Long orderId, LocalDateTime orderDate, OrderStatus orderStatus, Address address, int orderPrice, int count, String itemName) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.orderPrice = orderPrice;
        this.count = count;
        this.itemName = itemName;
    }

    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private int orderPrice;
    private int count;
    private String itemName;


}
