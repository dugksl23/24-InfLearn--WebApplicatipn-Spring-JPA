package JPA.Book.jpashop.item.dto;


import JPA.Book.jpashop.category.domain.Category;
import JPA.Book.jpashop.item.subItems.Book;
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
    private String author;
    private String isbn;
    private String name;
    private int price;
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
