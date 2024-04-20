package JPA.Book.jpashop.api.order.dto;


import JPA.Book.jpashop.api.orderItem.dto.OrderItemDto;
import JPA.Book.jpashop.item.adress.domain.Address;
import JPA.Book.jpashop.order.domain.Order;
import JPA.Book.jpashop.order.domain.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ApiOrderDto {

    private Long id;
    private LocalDateTime orderTime;
//    private int totalPrice;

    private String memberName;
    private Address address;

    private OrderStatus orderStatus;
    private List<OrderItemDto> orderItem = new ArrayList<>();

    public ApiOrderDto(Order order) {
        id = order.getId();
        orderTime = order.getOrderDate();
        memberName = order.getMember().getName();
        address = order.getDelivery().getDeliveryAddress();
        orderStatus = order.getOrderStatus();
        orderItem = order.getOrderItems().stream().map(OrderItemDto::new).toList();
    }
}
