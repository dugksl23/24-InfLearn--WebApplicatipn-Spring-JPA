package JPA.Book.jpashop.item.controller;


import JPA.Book.jpashop.item.domain.Item;
import JPA.Book.jpashop.item.dto.BookDto;
import JPA.Book.jpashop.item.dto.BookResponseDto;
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
        BookResponseDto bookResponseDto = new BookResponseDto().fromBook(book);
        model.addAttribute("bookResponseDto", bookResponseDto);
        return "item/updateItem";
    }

    @PostMapping("/update/{itemId}")
    public String updateItem(@ModelAttribute("bookResponseDto") BookDto bookDto) {
        Book book = bookDto.fromItemDto(bookDto);
        itemService.updateItem(book);
        return "redirect:/item/itemList";
    }


}
