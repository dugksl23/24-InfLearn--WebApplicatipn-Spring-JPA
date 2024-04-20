package JPA.Book.jpashop.api.orderItem.dto;

import JPA.Book.jpashop.api.item.dto.ApiItemDto;
import JPA.Book.jpashop.orderItem.domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    private Long id;
    private int price;
    private int count;
    private ApiItemDto item;

    public OrderItemDto(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.price = orderItem.getPrice();
        this.count = orderItem.getCount();
        this.item = new ApiItemDto(orderItem.getItem());
    }
}
