package JPA.Book.jpashop.item.dto;


import JPA.Book.jpashop.category.domain.Category;
import JPA.Book.jpashop.item.subItems.Book;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class BookResponseDto {
    
    private Long id;
    @NotBlank(message = "입력해주세요.")
    private String author;
    @NotBlank(message = "입력해주세요.")
    private String isbn;
    @NotBlank(message = "입력해주세요.")
    private String name;
    @NotNull
    @Min(value = 1, message = "입력해주세요.")
    private int price;
    @NotNull
    @Min(value = 1, message = "입력해주세요.")
    private int stockQuantity;
    private List<Category> categories = new ArrayList<>();


    public BookResponseDto fromBook(Book book) {
        BookResponseDto build = BookResponseDto.builder()
                .id(book.getId())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .stockQuantity(book.getStockQuantity())
                .categories(book.getCategories())
                .name(book.getName())
                .build();
        return build;
    }

}
