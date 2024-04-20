package JPA.Book.jpashop.api.order.dto;


import JPA.Book.jpashop.Member.domain.Member;
import JPA.Book.jpashop.item.adress.domain.Address;
import JPA.Book.jpashop.order.domain.Order;
import JPA.Book.jpashop.order.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiOrderDto {

    private Long id;
    private LocalDateTime orderTime;
//    private int totalPrice;

    private Member member;
    private Address address;

    private OrderStatus orderStatus;

    public ApiOrderDto(Order order) {
        this.id = order.getId();
//        this.totalPrice = order.getTotalPrice();
        this.orderTime = order.getOrderDate();
        this.member = order.getMember();
        this.address = order.getDelivery().getDeliveryAddress();
        this.orderStatus = order.getOrderStatus();
    }
}
