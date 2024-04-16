package JPA.Book.jpashop.category.domain;


import JPA.Book.jpashop.item.domain.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
//    @NotBlank(message = "입력해주세요.")
    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name="item_id"))
    //중간 테이블을 만드는 컬럼이며, 해당 테이블에는
    //category 테이블과 item 테이블의 기본키를 외래키로 모두 가지고 있다.
    private List<Item> items;

    //객체지향에서는 객체와 객체간의 다대다 관계가 가능하다. ex) 하기
    //관계형 DB에서는 관계를 양쪽에서 가질 수 없기 때문에
    //일대다 다대일로 풀어내는 중간 테이블이 필요하다.
    //-> 다만, 활용방법은 이게 다이며, 중간에 테이블에 무엇을 추가할 수도 없다. 현업에서는 사용 불가.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();


    // == 연관관계 편의 메서드 ==
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }

}
