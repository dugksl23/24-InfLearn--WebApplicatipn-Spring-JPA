package JPA.Book.jpashop.item.controller;


import JPA.Book.jpashop.item.domain.Item;
import JPA.Book.jpashop.item.dto.BookDto;
import JPA.Book.jpashop.item.service.ItemService;
import JPA.Book.jpashop.item.subItems.Book;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Builder
@RequiredArgsConstructor
@RequestMapping("/item")
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/register")
    public String registerItem(Model model, BookDto registerItemForm) {
        //view단에서 이미 타입을 구분했다고 가정.
        log.info("get 들어옴");
        model.addAttribute("registerItemForm", registerItemForm);
        return "/item/registerItem";
    }

    @PostMapping("/registerItem")
    public String registeredItem(@Validated @ModelAttribute("registerItemForm") BookDto registerItemForm, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            return "item/registerItem";
        }

        Item item = registerItemForm.registerItem();
        itemService.save(item);
        return "redirect:/";
    }


    @GetMapping("/itemList")
    public String itemList(Model model) {
        List<Item> itemList = itemService.findAll();
        model.addAttribute("itemList", itemList);
        return "item/itemList";
    }


    @GetMapping("/update/{itemId}")
    public String updateItemForm(@PathVariable(value = "itemId") Long itemId, Model model) {
        Book book = (Book) itemService.findById(itemId);
        BookDto bookDto = BookDto.fromBook(book);
        model.addAttribute("bookDto", bookDto);
        return "item/updateItem";
    }

    @PostMapping("/update/{itemId}")
    public String updateItem(@ModelAttribute("bookDto") @Validated BookDto bookDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "item/updateItem";
        }

        //Book book = bookDto.fromItemDto(bookDto); //되도록이면 Entity는 Web 계층에서 생성하지 않는 것이 원칙!
        itemService.updateItem(bookDto.getId(), bookDto.getName(), bookDto.getPrice(), bookDto.getStockQuantity());
        return "redirect:/item/itemList"; //redirect는 주소를 처음부터 다시 설정.
        //리다이렉트/겟 (Post/Redirect/Get, PRG):
        // 폼을 제출한 후에 새로고침으로 인한 중복 제출을 방지하기 위해 사용됩니다.
        // 사용자가 폼을 제출하면 서버는 처리하고 결과를 보여줍니다.
        // 이때 리다이렉션을 통해 사용자의 브라우저를 새로운 페이지로 이동시킵니다.
        // 그러면 사용자가 새로고침해도 이전에 제출한 내용이 다시 제출되지 않습니다.
    }


}
