package JPA.Book.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable //JPA의 내장 타입이며, 종속 관계의 주인의 임베디드 타입의 종속 변수로만 활용된다.
public class Address {

    private String street;
    private String city;
    private String zipCode;

}
