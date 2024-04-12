package JPA.Book.jpashop.item.subItems;

import JPA.Book.jpashop.item.domain.Item;
import jakarta.persistence.DiscriminatorValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@Builder
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("movie")
public class Movie extends Item {

    private Long id;
    private String director;
    private String actor;

}
