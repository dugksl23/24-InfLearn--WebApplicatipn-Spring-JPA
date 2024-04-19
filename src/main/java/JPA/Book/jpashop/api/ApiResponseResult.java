package JPA.Book.jpashop.api;


import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseResult<T> {
    private Long count;
    private T data;
}

