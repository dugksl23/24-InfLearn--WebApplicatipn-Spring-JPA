package JPA.Book.jpashop.api.order.dto;


import JPA.Book.jpashop.item.adress.domain.Address;
import JPA.Book.jpashop.order.domain.Order;
import JPA.Book.jpashop.order.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiSimpleOrderDto {

    private Long orderId;
    private Long memberId;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private String name;
    private Address deliveryAddress;

    public ApiSimpleOrderDto(Order order) {

        this.name = order.getMember().getName();
        this.orderDate = order.getOrderDate();
        this.memberId = order.getMember().getId();
        this.orderId = order.getId();
        this.orderStatus = order.getOrderStatus();
        this.deliveryAddress = order.getDelivery().getDeliveryAddress();

    }
}
