package JPA.Book.jpashop.item.subItems;

import JPA.Book.jpashop.item.domain.Item;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@DiscriminatorValue("book")
@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book extends Item  {

    private String author;
    private String isbn;

}