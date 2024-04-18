package JPA.Book.jpashop.order.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
    //기존 enum 타입으로 했을 경우에는, 값을 바인딩하지 못한다.

}
