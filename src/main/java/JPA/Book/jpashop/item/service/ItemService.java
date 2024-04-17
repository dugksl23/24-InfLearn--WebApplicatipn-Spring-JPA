package JPA.Book.jpashop.item.service;

import JPA.Book.jpashop.item.domain.Item;
import JPA.Book.jpashop.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) //기본 설정값을 readOnly로 하며, 각 메소드 단위에서 설정 변경.
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void save(Item item) {
        itemRepository.saveItem(item);
    }

    public List<Item> findAll() {
        List<Item> items = itemRepository.getAllItems();
        return items;
    }

    public Item findById(Long id) {
        Item byId = itemRepository.getItemById(id);
        return byId;
    }


    public Long saveItem(Item item) {
        Long l = itemRepository.saveItem(item);
        return l;
    }

    @Transactional
    public Long updateItem(Long id, String name, int price, int stockQuantity) {
        Item book = itemRepository.getItemById(id);
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        // -> 지금은 예제라서 setter를 사용하지만,
        // 되도록이면 change()와 같은 별도 함수로 필요한 값만 받아서 변경하는 것이 유지보수에 유리하다.
        return book.getId();
    }






}
