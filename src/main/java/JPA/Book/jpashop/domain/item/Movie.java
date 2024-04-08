package JPA.Book.jpashop.domain.item;


import JPA.Book.jpashop.Entity.Item;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@DiscriminatorValue("movie") //enum class를 상속받은 child class에서 db에 상속구조를 유지한채 해당 컬럼 타입으로 저장하기 위한 어노테이션
public class Movie extends Item {
    @Id @GeneratedValue
    private Long id;
    private String director;
    private String actor;

}
