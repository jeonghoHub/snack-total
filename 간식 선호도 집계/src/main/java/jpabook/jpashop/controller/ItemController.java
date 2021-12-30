package jpabook.jpashop.controller;


import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.snackDomain.SnackItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.Authenticator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new SnackItemForm());
        return "items/createItemForm";
    }

    @PostMapping("items/new")
    public ResponseEntity<Message> cteate(@RequestParam("file") MultipartFile files, SnackItemForm form) throws IOException {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        System.out.println("fileInfo>>>>>>>>>>>>>>" + files);
        String baseDir = "C:\\Users\\ServerFiles";
        String filePath = baseDir + "\\" + files.getOriginalFilename();
        files.transferTo(new File(filePath));
        form.setSellImgUrl(filePath);

        SnackItem item = new SnackItem();
        item.setName(form.getName());
        item.setFilePath(filePath);
        itemService.saveItem(item);

        message.setStatus(StatusEnum.OK);
        message.setMessage("성공코드");

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<SnackItem> items = itemService.findItems();
        model.addAttribute("items", items);

        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        SnackItem item = itemService.findOne(itemId);

        SnackItemForm form = new SnackItemForm();
        form.setId(item.getId());
        form.setName(item.getName());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") SnackItemForm form) {

        itemService.updateItem(itemId, form.getName());
        return "redirect:/items";
    }

    @RequestMapping("/items/delete")
    public ResponseEntity<Message> deleteItem(SnackItemForm form) {
        SnackItem one = itemService.findOne(form.getId());
        itemService.deleteOne(one.getId());

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();

        message.setStatus(StatusEnum.OK);
        message.setMessage("성공코드");

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
}
