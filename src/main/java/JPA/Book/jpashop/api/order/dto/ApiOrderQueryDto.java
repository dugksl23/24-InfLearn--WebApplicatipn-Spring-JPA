package JPA.Book.jpashop.api.order.dto;


import JPA.Book.jpashop.item.adress.domain.Address;
import JPA.Book.jpashop.order.domain.Order;
import JPA.Book.jpashop.order.domain.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiOrderQueryDto {


    private Long id;
    private String memberName;
    private LocalDateTime orderTime;
    private Address address;
    private OrderStatus orderStatus;

    public ApiOrderQueryDto(Order order) {
        //**Dto는 Entity를 참조할 수 있다.
        this.id = order.getId();
        this.orderTime = order.getOrderDate();
        this.memberName = order.getMember().getName();
        this.address = order.getDelivery().getDeliveryAddress();
        this.orderStatus = order.getOrderStatus();
    }

    public ApiOrderQueryDto(Long id, String memberName, LocalDateTime orderTime, Address address, OrderStatus orderStatus) {
        this.id = id;
        this.memberName = memberName;
        this.orderTime = orderTime;
        this.address = address;
        this.orderStatus = orderStatus;
    }
}
