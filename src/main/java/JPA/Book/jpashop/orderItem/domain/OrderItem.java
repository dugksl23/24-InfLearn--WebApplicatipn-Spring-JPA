package JPA.Book.jpashop.orderItem.domain;

import JPA.Book.jpashop.item.domain.Item;
import JPA.Book.jpashop.order.domain.Order;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id @GeneratedValue
    private long id;

    @Column(name="order_item_price")
    private long price;
    @Column(name="order_item_count")
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="orders_id")
    @Setter
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    // === 비지니스 로직 ===
    public void cancel(){
        getItem().addStockQuantity(count); //주문(서)한 item의 수량을 원복해줘야 한다.
    }

    public int getTotalPrice(){
        return (int) (getCount() * getPrice());
    }


    // == 생성 메서드 ==
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = OrderItem.builder()
                .item(item)
                .count(count)
                .price(orderPrice)
                .build();

        // == 아이템의 수량 감소 로직 ==
        item.reduceStockQuantity(count);
        return orderItem;

    }

}
