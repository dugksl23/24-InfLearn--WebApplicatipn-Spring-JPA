package JPA.Book.jpashop.api.order;


import JPA.Book.jpashop.api.order.dto.ApiOrderDto;
import JPA.Book.jpashop.api.order.dto.ApiOrderResultResponse;
import JPA.Book.jpashop.api.order.dto.ApiSimpleOrderDto;
import JPA.Book.jpashop.order.domain.Order;
import JPA.Book.jpashop.order.domain.OrderSearch;
import JPA.Book.jpashop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 연관관계 조회
 * 1. 특징
 * - @xToOne
 * 2. 순서
 * - Order -> member   @ManyToOne
 * - Order -> Delivery @OneToOne
 */
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @GetMapping("/v1/orderList")
    public List<Order> findAllOrderListV1() {
        OrderSearch orderSearch = new OrderSearch();
        List<Order> list = orderService.findOrders(orderSearch);
        // lazy (프록시 객체) 강제 초기화
        list.stream().forEach(order -> order.getMember().getName());
        list.stream().forEach(order -> order.getDelivery().getStatus());
        return list;
    }

    @GetMapping("/v2/orderList")
    public ApiOrderResultResponse findAllOrderListV2() {
        OrderSearch orderSearch = new OrderSearch();
        List<ApiSimpleOrderDto> collect = orderService.findOrders(orderSearch)
                .stream().map(ApiSimpleOrderDto::new).toList();
                // Java 8의 람다 레퍼런스
                // ApiSimpleOrderDto::new
        int size = collect.size();
        return new ApiOrderResultResponse<>(size, collect);
    }

    @GetMapping("/v3/orderList")
    public ApiOrderResultResponse findAllOrderFetchMemberDeliveryV3() {
        OrderSearch orderSearch = new OrderSearch();
        List<ApiOrderDto> list = orderService.findAllOrderFetchMemberDeliveryV3(orderSearch);
        return new ApiOrderResultResponse<>(list.size(), list);
    }

}
