package JPA.Book.jpashop.api.order.controller;

import JPA.Book.jpashop.api.order.dto.ApiOrderDto;
import JPA.Book.jpashop.api.order.dto.ApiOrderResultResponse;
import JPA.Book.jpashop.order.domain.Order;
import JPA.Book.jpashop.order.domain.OrderSearch;
import JPA.Book.jpashop.order.query.OrderQueryDto;
import JPA.Book.jpashop.order.service.OrderService;
import JPA.Book.jpashop.orderItem.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order/")
@RequiredArgsConstructor
@Slf4j
public class OrderApiController {

    private final OrderService orderService;


    // Entity 노출로 인해서 사용을 지양.
    @RequestMapping("v1/orderList")
    public ApiOrderResultResponse findOrderListV1(OrderSearch orderSearch) {
        List<Order> orders = orderService.findOrders(orderSearch);
        int size = orders.size();
        // lazy (프록시 객체) 강제 초기화
        for (Order order : orders) {
            order.getDelivery().getDeliveryAddress();
            order.getMember().getName();
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                orderItem.getItem().getName();
            }
        }
        return new ApiOrderResultResponse(size, orders);
    }


    @RequestMapping("/v2/orderList")
    public ApiOrderResultResponse findOrderListV2(OrderSearch orderSearch) {
        List<ApiOrderDto> list = orderService.findOrders(orderSearch).stream()
                .map(ApiOrderDto::new).toList();
        int size = list.size();
        return new ApiOrderResultResponse<>(size, list);

    }

    @RequestMapping("/v3/orderList") // Fetch Join을 통한 성능 최적화
    public ApiOrderResultResponse findOrderListV3(OrderSearch orderSearch) {
        List<ApiOrderDto> list = orderService.findAllOrderWithItem(orderSearch);
        return new ApiOrderResultResponse<>(list.size(), list);
    }


    /**
     * xToOne 관계에 있는 연관관계 Entity들을 Fetch Join으로 즉시 로딩.
     * -> xToOne 관계는 페이징에 영향을 주지 않는다.
     */
    @RequestMapping("/v3/orderList/page") // Fetch Join을 통한 성능 최적화
    public ApiOrderResultResponse findOrderListWithPagingV3(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100")  int limit) {
        List<ApiOrderDto> allOrderFetchMemberDeliveryV3 =
                orderService.findAllOrderFetchMDWithPaging(offset, limit);
        int size = allOrderFetchMemberDeliveryV3.size();
        return new ApiOrderResultResponse(size, allOrderFetchMemberDeliveryV3);
    }

    @GetMapping("/v4/orderList")
    public ApiOrderResultResponse findOrderListV4(){
        List<OrderQueryDto> allOrderDto = orderService.findAllOrderQueryDtoV4();
        int size = allOrderDto.size();
        return new ApiOrderResultResponse(size, allOrderDto);
    }

}
