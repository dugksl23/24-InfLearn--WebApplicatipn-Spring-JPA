package JPA.Book.jpashop.item.subItems;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAlbum is a Querydsl query type for Album
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlbum extends EntityPathBase<Album> {

    private static final long serialVersionUID = 586080973L;

    public static final QAlbum album = new QAlbum("album");

    public final JPA.Book.jpashop.item.domain.QItem _super = new JPA.Book.jpashop.item.domain.QItem(this);

    public final StringPath artist = createString("artist");

    //inherited
    public final ListPath<JPA.Book.jpashop.category.domain.Category, JPA.Book.jpashop.category.domain.QCategory> categories = _super.categories;

    public final StringPath etc = createString("etc");

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final NumberPath<Integer> price = _super.price;

    //inherited
    public final NumberPath<Integer> stockQuantity = _super.stockQuantity;

    public QAlbum(String variable) {
        super(Album.class, forVariable(variable));
    }

    public QAlbum(Path<? extends Album> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAlbum(PathMetadata metadata) {
        super(Album.class, metadata);
    }

}

