package JPA.Book.jpashop.item.dto;


import JPA.Book.jpashop.item.domain.Item;
import JPA.Book.jpashop.item.subItems.Book;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
public class BookDto extends ItemDto {

    private Long id;
    @NotBlank(message = "입력해주세요.")
    private String author;
    @NotBlank(message = "입력해주세요.")
    private String isbn;

    //    @NotBlank(message = "입력해주세요.")
    //    private String name;
    //    부모 class의 속성값과 어노테이션 설정을 모두 이어받는다.

//    @NotBlank
//    private int price;
//    @NotBlank
//    private int stockQuantity;

    public Item registerItem() {

        //==　1. view 에서 특정 Item (Book) 을 등록 ==
        Item book = Book.builder()
                .author(getAuthor()).isbn(getIsbn()).build();
        //== 2. view에서 아이템의 종류(Book)의 수량과 가격을 등록
        book.setPrice(getPrice());
        book.setStockQuantity(getStockQuantity());
        book.getCategories();
        book.setName(getName());
        //== 3. Item 객체 Entity로 값을 반환.
        return book;
    }

    public static Book fromItemDto(BookDto bookDto) {
        Book book = Book.builder()
                .author(bookDto.getAuthor())
                .isbn(bookDto.getIsbn())
                .build();
        book.setId(bookDto.getId());
        book.setPrice(bookDto.getPrice());
        book.setStockQuantity(bookDto.getStockQuantity());
        book.setName(bookDto.getName());
        return book;
    }

    public static BookDto fromBook(Book book) {
        BookDto build = BookDto.builder()
                        .id(book.getId())
                        .author(book.getAuthor())
                        .isbn(book.getIsbn())
                        .build();
        build.setId(book.getId());
        build.setPrice(book.getPrice());
        build.setStockQuantity(book.getStockQuantity());
        build.setName(book.getName());
        return build;
    }
}
