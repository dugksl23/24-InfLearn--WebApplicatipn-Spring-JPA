package JPA.Book.jpashop.api.order.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiOrderResultResponse<T> {
    private int count;
    private T data;
}


