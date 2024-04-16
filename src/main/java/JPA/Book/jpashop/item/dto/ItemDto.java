package JPA.Book.jpashop.item.dto;


import JPA.Book.jpashop.category.domain.Category;
import JPA.Book.jpashop.category.domain.ItemCategory;
import JPA.Book.jpashop.item.domain.Item;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class ItemDto {

    @NotBlank(message = "입력해주세요.")
    private String name;
    //@NotNull(message = "입력해주세요.")
    @Min(value = 0, message = "입력해주세요.")
    private int price;
    @NotNull(message = "입력해주세요.")
    private int stockQuantity = 0;
    private Category category;

    public Item toSubItem() {
        Item item;
        if (getCategory().getName().equals(ItemCategory.BOOK.toString())) {
            item = new BookDto().registerItem(this);
        } else {
            throw new IllegalStateException("Unexpected value: " + getCategory());
        }

        return item;
    }

}
