package JPA.Book.jpashop.item.subItems;

import JPA.Book.jpashop.item.domain.Item;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@DiscriminatorValue("movie")
@Entity
@Getter
@Setter
public class Movie extends Item {

    private String director;
    private String actor;

}
