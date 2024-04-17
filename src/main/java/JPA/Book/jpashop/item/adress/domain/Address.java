package JPA.Book.jpashop.item.adress.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable //JPA의 내장 타입이며, 종속 관계의 주인의 임베디드 타입의 종속 변수로만 활용된다.
//@AllArgsConstructor //모든 파라미터를 가진 생성자
//@NoArgsConstructor // public 기본 생성자
public class Address {

    @NotBlank(message = "입력해주세요")
    private String city;
    private String street;
    private String zipCode;

    protected Address() {
    }

    public Address(String city, String street, String zipCode) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

    public Address(Address address) {
        this.street = address.getStreet();
        this.city = address.getCity();
        this.zipCode = address.getZipCode();
    }
    //임베디드 값 타입은 최초 입력 이후 변경되면 안된다(Immutable)
    //최초 입력 시 값을 입력하도록 기본 생성자를 통해서

}
