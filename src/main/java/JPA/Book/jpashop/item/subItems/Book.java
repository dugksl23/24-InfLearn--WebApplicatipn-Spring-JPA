package JPA.Book.jpashop.item.subItems;

import JPA.Book.jpashop.item.domain.Item;
import jakarta.persistence.DiscriminatorValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@DiscriminatorValue("book")
//@Builder
public class Book extends Item {

    private Long id;
    private String author;
    private String title;
    private String isbn;

}