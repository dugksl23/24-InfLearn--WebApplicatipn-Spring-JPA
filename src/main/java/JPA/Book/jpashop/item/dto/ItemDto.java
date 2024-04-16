package JPA.Book.jpashop.item.dto;


import JPA.Book.jpashop.category.domain.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @Min(value = 1, message = "입력해주세요.")
//    @NotNull(message = "입력해주세요.")
    private int stockQuantity;
    @Min(value = 1, message = "입력해주세요.")
    private int price;
    // * 임베디드 타입은 filed error 지원이 되지 않는듯하다.
    private Category category;

}
