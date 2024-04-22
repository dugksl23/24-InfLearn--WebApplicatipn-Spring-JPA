package JPA.Book.jpashop.order.service;


import JPA.Book.jpashop.Member.domain.Member;
import JPA.Book.jpashop.Member.repository.MemberRepository;
import JPA.Book.jpashop.api.order.dto.ApiOrderDto;
import JPA.Book.jpashop.api.order.dto.ApiOrderQueryDto;
import JPA.Book.jpashop.api.order.repository.OrderQueryRepository;
import JPA.Book.jpashop.delivery.domain.Delivery;
import JPA.Book.jpashop.delivery.domain.DeliveryStatus;
import JPA.Book.jpashop.item.domain.Item;
import JPA.Book.jpashop.item.repository.ItemRepository;
import JPA.Book.jpashop.order.domain.Order;
import JPA.Book.jpashop.order.domain.OrderSearch;
import JPA.Book.jpashop.order.query.OrderQueryDto;
import JPA.Book.jpashop.order.repository.OrderRepository;
import JPA.Book.jpashop.orderItem.domain.OrderItem;
import JPA.Book.jpashop.orderItem.query.OrderItemQueryDto;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final EntityManager em;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderQueryRepository orderQueryRepository;


    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int orderStock) {
        // Member 조회
        Member member = memberRepository.findMemberById(memberId);
        // 아이템 조회
        Item item = itemRepository.getItemById(itemId);
        //배송 정보 조회
        Delivery delivery = Delivery.builder().deliveryAddress(member.getAddress()).status(DeliveryStatus.READY).build();

        // === 1. 주문 상품 생성 ===
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), orderStock);
        //-> List<OrderItem> orderItems = createOrderItem에서 list로 처리해야 한다.
        // === 2. 주문을 생성 ===
        Order order = Order.createOrder(member, delivery, orderItem);
        Map<Object, String> productInfo = new HashMap<>();
        // === 3. 주문을 저장  ==
        Long orderId = orderRepository.saveOrder(order);
        //Order의 Cascase 옵션에 의해 연관 관계에 있는 모든 Entity의 데이터가 영속화된다.

        // === 4. cascade의 범위 ===
        // 해당 필드값들을 Private owner만 embadded 값으로만 참조된다면 cascade 사용을 권장.
        // 즉 라이프사이클이 private Owner(order)에 국한된다면 사용가능.
        Order orderById = orderRepository.findOrderById(orderId);
        return orderId;
    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId) {
        Order orderById = orderRepository.findOrderById(orderId);
        Delivery delivery = orderById.getDelivery();
        orderById.cancelOrder(orderById.getMember(), orderById.getDelivery());
    }


    //주문 조회
    public Order searchOrder(String keyward) {
        //em.find();
        return null;
    }

    //주문 전체 조회
    public List<Order> findAll() {
        return em.createQuery("select o from Order o", Order.class).getResultList();
    }

    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        List<Order> allOrders = orderRepository.findAllOrders(orderSearch);

        return allOrders;
    }

    public List<ApiOrderDto> findAllOrderFetchMemberDeliveryV3(OrderSearch orderSearch) {
        return orderRepository.findAllOrdersFetchJoinMemberDelivery(orderSearch).stream()
                .map(ApiOrderDto::new).toList();
    }

    /**
     * 1. orderQueryRepository　용 메서드이자, 성능 최적화를 위한 것
     * 2. Entity Repository 와 Query Repository 역할을 분리.
     */
    public List<ApiOrderQueryDto> findAllOrderDtoV4(OrderSearch orderSearch) {
        return orderQueryRepository.findAllOrderDto(orderSearch);
    }


    /**
     * @xToMany 일 경우에는, distinct 키워드를 통해 중복 제거 필수.
     */
    public List<ApiOrderDto> findAllOrderWithItem(OrderSearch orderSearch) {
        List<ApiOrderDto> list = orderRepository.findAllOrderWithItem(orderSearch).stream()
                .map(ApiOrderDto::new).toList();
        log.info("size : {}", list.size());
        for (ApiOrderDto order : list) {
            log.info("주소값 ref : {}, order Id : {}", order, order.getId());
            log.info("item count : {}", order.getOrderItems().size());
            order.getOrderItems().forEach(i ->
                    log.info("item name : {}", i.getItem().getName()));
        }
        return list;
    }

    public List<ApiOrderDto> findAllOrderFetchMDWithPaging(int offset, int limit) {
        return em.createQuery("select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList()
                .stream().map(ApiOrderDto::new).toList();
    }


    public List<OrderQueryDto> findAllOrderQueryDtoV4() {
        return orderQueryRepository.findOrderQueryDtoList();
    }

    public List<OrderQueryDto> findAllByDto_optimization() {
        //0. fetch Join으로 member와 delivery를 조인하여 dto로 변환후 return.
        List<OrderQueryDto> orderQueryDtoList = orderQueryRepository.findAllOrderQueryDto();

        //1. order를 다 가지고 오고 id만 따로 뽑은 list를 만든다.
        List<Long> orderIdList = getOrderIdList();

        // 2. orderIdList　를 In query 로 item　을 한번에 전부 가져온다.
        List<OrderItemQueryDto> orderItemDtoList = getOrderItemDtoList(orderIdList);

        // 3. stream의 Collector의 groupby를 기준으로 orderId를 기준으로 정렬하여 map으로 변환.
        Map<Long, List<OrderItemQueryDto>> orderItemMap = getOrderItemMap(orderItemDtoList);

        // 4.  메모리 Map에서, orderQueryDto List에 orderId와 맞는 orderItem을 셋팅한다.
        orderQueryDtoList.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));
        // -> query가 총 2번 나간다.
        return null;
    }

    private static Map<Long, List<OrderItemQueryDto>> getOrderItemMap(List<OrderItemQueryDto> orderItemDtoList) {
        return orderItemDtoList.stream()
                .collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
    }

    private List<OrderItemQueryDto> getOrderItemDtoList(List<Long> orderIdList) {
        return em.createQuery("select new JPA.Book.jpashop.orderItem.query.OrderItemQueryDto(oi.order.id, i.name, oi.price, oi.count) from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIdList)
                .getResultList();
    }

    private List<Long> getOrderIdList() {
        return orderQueryRepository.findAllOrder().stream()
                .map(o -> o.getId()).toList();
    }
}

