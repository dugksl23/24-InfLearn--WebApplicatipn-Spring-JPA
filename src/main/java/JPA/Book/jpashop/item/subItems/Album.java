package JPA.Book.jpashop.item.subItems;

import JPA.Book.jpashop.item.domain.Item;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@DiscriminatorValue("album")
@Entity
public class Album extends Item {

    private String artist;
    private String etc;

}