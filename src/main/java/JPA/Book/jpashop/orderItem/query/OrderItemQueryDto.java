package JPA.Book.jpashop.orderItem.query;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

// view에 뿌리기 위한 dto
public class OrderItemQueryDto {

    private Long orderId;
    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemQueryDto(Long orderId, String itemName, int orderPrice, int count) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
