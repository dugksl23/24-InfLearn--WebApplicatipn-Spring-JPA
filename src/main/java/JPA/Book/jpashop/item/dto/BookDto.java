package JPA.Book.jpashop.item.dto;


import JPA.Book.jpashop.item.domain.Item;
import JPA.Book.jpashop.item.subItems.Book;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto extends ItemDto {

    @NotBlank(message = "입력해주세요.")
    private String title;
    @NotBlank(message = "입력해주세요.")
    private String author;
    @NotBlank(message = "입력해주세요.")
    private String isbn;

//    @NotBlank
//    private int price;
//    @NotBlank
//    private int stockQuantity;

    public Item registerItem(ItemDto itemDto) {

        //==　1. view 에서 특정 Item (Book) 을 등록 ==
        Item book = Book.builder()
                .author("Yohan").isbn("ABCD").build();
        //== 2. view에서 아이템의 종류(Book)의 수량과 가격을 등록
        book.setPrice(itemDto.getPrice());
        book.setStockQuantity(itemDto.getStockQuantity());
        book.getCategories();
        book.setName(itemDto.getName());
        //== 3. Item 객체 Entity로 값을 반환.
        return book;
    }
}
