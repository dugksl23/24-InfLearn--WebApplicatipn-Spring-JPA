package JPA.Book.jpashop.order.controller;

import JPA.Book.jpashop.Member.domain.Member;
import JPA.Book.jpashop.Member.services.MemberService;
import JPA.Book.jpashop.item.domain.Item;
import JPA.Book.jpashop.item.service.ItemService;
import JPA.Book.jpashop.order.domain.Order;
import JPA.Book.jpashop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderService orderService;

    @GetMapping("/createOrder")
    public String createOrder(Model model) {

        List<Member> allMember = memberService.findAllMember();
        List<Item> all = itemService.findAll();
        model.addAttribute("members", allMember);
        model.addAttribute("items", all);

        return "order/order";
    }

    @PostMapping("/createOrder")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count, Model model) {
                        //@RequestParam("") 어노테이션은 view form submit 방식으로서
                        // 하위 태그의 name 속성과 매칭한다.
        orderService.order(memberId, itemId, count);
        //Web 계층에서는 가급적 서비스단에서 필요한 식별자 값만 넘기고, Service 단의  @Transactional 환경 아래에서
        //데이터 조회 및 변경(영속상태 유지)과 관련된 비지니스 로직을 수행하는 것이 바람직하다.

        // web 계층에서 memberService.findOne(id); 를 통해 member 객체를 넘기면??
        // --> 결론은 @transactional 환경에서 관리되지 않은 값이 넘어온 것이기에
        //     변경사항에 관하여 변경 감지 기능이 작동하지 않는다.
        //     모든 변경사항은 @Transactional 환경 아래에서 이루어져야 한다.
        return "redirect:/order/orderList";
    }

    @GetMapping("/orderList")
    public String orderList(@RequestParam("memberId") Long memberId, Model model) {
        List<Order> all = orderService.findAll();
        model.addAttribute("orderSearch", all);
//        orderService.searchOrder();
        return "order/orderList";

    }


}
