package JPA.Book.jpashop.item.domain.dto;

import JPA.Book.jpashop.category.domain.Category;
import JPA.Book.jpashop.item.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ItemDto {

    private String name;
    private int price;
    private int stockQuantity;
    private List<Category> categories = new ArrayList<>();

    public Item toItemEntity(){
        return Item.builder()
                .name(this.name)
                .price(this.price)
                .stockQuantity(this.stockQuantity)
                .categories(this.categories)
                .build();
    }


}
