package JPA.Book.jpashop.domain.item;

import JPA.Book.jpashop.Entity.Item;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@DiscriminatorValue("album")
public class Album extends Item {

    @Id @GeneratedValue
    private Long id;
    private String artist;
    private String etc;

}
