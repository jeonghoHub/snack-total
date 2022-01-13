package jpabook.jpashop.service;

import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.snackDomain.SnackItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(SnackItem snackItem) {
        itemRepository.save(snackItem);
    }

    @Transactional
    public void updateItem(Long itemId, String name, String category, String filePath) {
        SnackItem findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setCateGory(category);
        findItem.setFilePath(filePath);
    }

    public List<SnackItem> findItems(){
        return itemRepository.findAll();
    }

    public SnackItem findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    @Transactional
    public void deleteOne(Long itemId) {
        itemRepository.deleteOne(itemId);
    }
}
