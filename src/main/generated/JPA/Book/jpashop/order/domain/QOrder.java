package JPA.Book.jpashop.order.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = 845348787L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final JPA.Book.jpashop.delivery.domain.QDelivery delivery;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final JPA.Book.jpashop.Member.domain.QMember member;

    public final DateTimePath<java.time.LocalDateTime> orderDate = createDateTime("orderDate", java.time.LocalDateTime.class);

    public final ListPath<JPA.Book.jpashop.orderItem.domain.OrderItem, JPA.Book.jpashop.orderItem.domain.QOrderItem> orderItems = this.<JPA.Book.jpashop.orderItem.domain.OrderItem, JPA.Book.jpashop.orderItem.domain.QOrderItem>createList("orderItems", JPA.Book.jpashop.orderItem.domain.OrderItem.class, JPA.Book.jpashop.orderItem.domain.QOrderItem.class, PathInits.DIRECT2);

    public final EnumPath<OrderStatus> orderStatus = createEnum("orderStatus", OrderStatus.class);

    public final DateTimePath<java.time.LocalDateTime> orderUpdateDate = createDateTime("orderUpdateDate", java.time.LocalDateTime.class);

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.delivery = inits.isInitialized("delivery") ? new JPA.Book.jpashop.delivery.domain.QDelivery(forProperty("delivery"), inits.get("delivery")) : null;
        this.member = inits.isInitialized("member") ? new JPA.Book.jpashop.Member.domain.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

