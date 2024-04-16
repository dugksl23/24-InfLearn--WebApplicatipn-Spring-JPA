package JPA.Book.jpashop.item.controller;


import JPA.Book.jpashop.item.domain.Item;
import JPA.Book.jpashop.item.dto.BookDto;
import JPA.Book.jpashop.item.dto.ItemDto;
import JPA.Book.jpashop.item.service.ItemService;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Builder
@RequiredArgsConstructor
@RequestMapping("/item")
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/register")
    public String registerItem(Model model, BookDto registerItemForm){
        //view단에서 이미 타입을 구분했다고 가정.
        log.info("get 들어옴");
        model.addAttribute("registerItemForm",  registerItemForm);
        return "/item/registerItem";
    }

    @PostMapping("/registerItem")
    public String registeredItem(Model model, @Valid ItemDto itemDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "item/registerItem";
        }

        Item item = itemDto.toSubItem();
        itemService.save(item);
        return "redirect:/";
        //== 궁금증?
        //book이 아닌 다른 아이템들로 등록을 하고자 할 경우에는 어떻게 처리?
        // 각각의 dto를 모두 등록할 수 없는데 어떻게 해야 할까?
        // --> 팩토리 패턴(?)
    }



}
