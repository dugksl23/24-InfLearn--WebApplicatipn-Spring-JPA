package JPA.Book.jpashop.item.domain;

import JPA.Book.jpashop.category.domain.Category;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Inheritance //default는 Single_Table : 하나의 테이블에 모두 넣기.
@DiscriminatorColumn(name = "dtype") //db에 서브타입을 구분하기 위해 사용되는 컬럼이며, 부모에게 사용.
public abstract class Item {
    //상속을 받는 class들은 Item Entity로 인해 객체화한 후, 저장이 되기에
    //상속 class들은 별도 id를 요구하지 않는다.
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();


}
