package jpabook.jpashop.controller;

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
    public ResponseEntity<Message> create(@RequestParam("file") MultipartFile files, SnackItemForm form) throws IOException {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        System.out.println("fileInfo>>>>>>>>>>>>>>" + files);
        String baseDir = "C:\\Users\\abc\\Desktop\\github간식 선호도 프로젝트\\간식 선호도 집계\\src\\main\\resources\\static\\image";
        String filePath = baseDir + "\\" + files.getOriginalFilename();
        SnackItem item = new SnackItem();
        item.setName(form.getName());
        try {
            files.transferTo(new File(filePath));
            item.setFilePath("/image/"+files.getOriginalFilename());
        } catch (IOException e) {

        }
        itemService.saveItem(item);

        message.setStatus(StatusEnum.OK);
        message.setMessage("성공코드");

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
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
    public String updateItem(@RequestParam("file") MultipartFile files, @PathVariable Long itemId, @ModelAttribute("form") SnackItemForm form) {

        System.out.println("fileInfo>>>>>>>>>>>>>>" + files);
        String baseDir = "C:\\Users\\abc\\Desktop\\github간식 선호도 프로젝트\\간식 선호도 집계\\src\\main\\resources\\static\\image";
        String uplodadfilePath = baseDir + "\\" + files.getOriginalFilename();
        String showFilePath = "/image/"+files.getOriginalFilename();
        try {
            files.transferTo(new File(uplodadfilePath));
        } catch (IOException e) {
            showFilePath = null;
        }
        itemService.updateItem(itemId, form.getName(), showFilePath);

        return "redirect:/votePage";
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
