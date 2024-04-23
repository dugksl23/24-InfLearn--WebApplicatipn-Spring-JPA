package JPA.Book.jpashop.delivery.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDelivery is a Querydsl query type for Delivery
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDelivery extends EntityPathBase<Delivery> {

    private static final long serialVersionUID = 1469447251L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDelivery delivery = new QDelivery("delivery");

    public final JPA.Book.jpashop.item.adress.domain.QAddress deliveryAddress;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final JPA.Book.jpashop.order.domain.QOrder order;

    public final EnumPath<DeliveryStatus> status = createEnum("status", DeliveryStatus.class);

    public QDelivery(String variable) {
        this(Delivery.class, forVariable(variable), INITS);
    }

    public QDelivery(Path<? extends Delivery> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDelivery(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDelivery(PathMetadata metadata, PathInits inits) {
        this(Delivery.class, metadata, inits);
    }

    public QDelivery(Class<? extends Delivery> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.deliveryAddress = inits.isInitialized("deliveryAddress") ? new JPA.Book.jpashop.item.adress.domain.QAddress(forProperty("deliveryAddress")) : null;
        this.order = inits.isInitialized("order") ? new JPA.Book.jpashop.order.domain.QOrder(forProperty("order"), inits.get("order")) : null;
    }

}

