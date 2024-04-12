package JPA.Book.jpashop.item.subItems;

import JPA.Book.jpashop.item.domain.Item;
import jakarta.persistence.DiscriminatorValue;
import lombok.Getter;

@Getter
@DiscriminatorValue("album")
public class Album extends Item {

    private Long id;
    private String artist;
    private String etc;

}