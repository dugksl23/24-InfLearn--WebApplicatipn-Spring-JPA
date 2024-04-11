package JPA.Book.jpashop.item.domain.subItems;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable //JPA의 내장 타입이며, 종속 관계의 주인의 임베디드 타입의 종속 변수로만 활용된다.
//@AllArgsConstructor //모든 파라미터를 가진 생성자
//@NoArgsConstructor // public 기본 생성자
public class Address {

    private String street;
    private String city;
    private String zipCode;

    protected Address() {
    }

    public Address(String street, String city, String zipCode) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }
    //임베디드 값 타입은 최초 입력 이후 변경되면 안된다(Immutable)
    //최초 입력 시 값을 입력하도록 기본 생성자를 통해서

}
