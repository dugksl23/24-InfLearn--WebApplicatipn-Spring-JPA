package JPA.Book.jpashop.api.member;


import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseResult<T> {
    private Long count;
    private T data;
}

