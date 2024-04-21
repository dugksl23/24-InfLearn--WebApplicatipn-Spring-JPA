package JPA.Book.jpashop.item.domain;

import JPA.Book.jpashop.category.domain.Category;
import JPA.Book.jpashop.exception.NotEnoughStockQuantityException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;


@BatchSize(size = 100)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance //default는 Single_Table : 하나의 테이블에 모두 넣기.
@DiscriminatorColumn(name = "dtype") //db에 서브타입을 구분하기 위해 사용되는 컬럼이며, 부모에게 사용.
@Setter
@AllArgsConstructor
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

    // === 비지니스 로직 ===
    /* 재고 증가 비지니스 로직 */
    // 내부에 비지니스 로직을 두는 이유?
    //  -> 재고 수량은 Item 엔티티와 관련된 것이기에 내부에서 로직을 처리하는 것이 객체지향적이며, 응집력이 생긴다.
    public void addStockQuantity(int quantity) {
        this.stockQuantity = this.stockQuantity + quantity;
    }

    /* 재고 감소 비지니스 로직 */
    public void reduceStockQuantity(int quantity) throws NotEnoughStockQuantityException {
        if(this.stockQuantity - quantity < 0){
           throw new NotEnoughStockQuantityException("need more stock ");
        }
        this.stockQuantity = this.stockQuantity - quantity;
    }




}
