package JPA.Book.jpashop.api.item.dto;

import JPA.Book.jpashop.item.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiItemDto {

    private String name;
    private int stockQuantity;
    private int price;

    public ApiItemDto(Item item) {
        this.name = item.getName();
        this.stockQuantity = item.getStockQuantity();
        this.price = item.getPrice();
    }
}
