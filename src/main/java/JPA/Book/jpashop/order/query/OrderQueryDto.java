package JPA.Book.jpashop.order.query;

import JPA.Book.jpashop.item.adress.domain.Address;
import JPA.Book.jpashop.order.domain.OrderStatus;
import JPA.Book.jpashop.orderItem.query.OrderItemQueryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@Setter

// view에 뿌리기 위한 dto
public class OrderQueryDto {

    private Long orderId;
    private String memberName;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemQueryDto> orderItems = new ArrayList<>();

    public OrderQueryDto(Long orderId, String memberName, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.memberName = memberName;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
