package JPA.Book.jpashop.item.subItems;

import JPA.Book.jpashop.item.domain.Item;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@DiscriminatorValue("book")
@Entity
@Getter @Setter
public class Book extends Item  {

    private String author;
    private String title;
    private String isbn;

}