package jpabook.jpashop.controller;

import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.snackDomain.SnackItem;
import jpabook.jpashop.snackDomain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.Authenticator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;

    @GetMapping("items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new SnackItemForm());
        return "items/createItemForm";
    }

    @PostMapping("items/new")
    public ResponseEntity<Message> create(@RequestParam("file") MultipartFile files, SnackItemForm form) throws IOException {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        String baseDir = "C:\\Users\\abc\\Desktop\\github간식 선호도 프로젝트\\간식 선호도 집계\\src\\main\\resources\\static\\image";
        String filePath = baseDir + "\\" + files.getOriginalFilename();
        System.out.println("@@@@@@@@@@@@@@@@  >>> " + files.getOriginalFilename());
        SnackItem item = new SnackItem();
        item.setName(form.getName());
        item.setCateGory(form.getCategory());
        try {
            files.transferTo(new File(filePath));
            item.setFilePath("/image/"+files.getOriginalFilename());
        } catch (IOException e) {
            if(files.getOriginalFilename().equals("")){
                item.setFilePath(null);
            } else {
                item.setFilePath("/image/"+files.getOriginalFilename());
            }
        }
        itemService.saveItem(item);

        message.setStatus(StatusEnum.OK);
        message.setMessage("성공코드");

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        SnackItem item = itemService.findOne(itemId);
        System.out.println(">>>>>"+ item.getFilePath());

        SnackItemForm form = new SnackItemForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setCategory(item.getCateGory());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@RequestParam("file") MultipartFile files,
                             @PathVariable Long itemId,
                             @ModelAttribute("form") SnackItemForm form,
                             @RequestParam(name = "originalFileDivi") Boolean originalFileDivi) {

        System.out.println("fileInfo>>>>>>>>>>>>>>" + originalFileDivi);
        System.out.println("fileInfo>>>>>>>>>>>>>>" + files.getOriginalFilename());
        String baseDir = "C:\\Users\\abc\\Desktop\\github간식 선호도 프로젝트\\간식 선호도 집계\\src\\main\\resources\\static\\image";
        String uplodadfilePath = baseDir + "\\" + files.getOriginalFilename();
        String showFilePath = "/image/"+files.getOriginalFilename();
        try {
            //이미 동일한 파일이 존재하면 Exception발생
            files.transferTo(new File(uplodadfilePath));
        } catch (IOException e) {
            //catch 후 이미지 경로 컬럼에만 해당이미지 경로 값 세팅
            if(files.getOriginalFilename().equals("")){
                if(!originalFileDivi) {
                    showFilePath = null;
                }
            } else {
                showFilePath = "/image/"+files.getOriginalFilename();
            }
        }
        itemService.updateItem(itemId, form.getName(), form.getCategory(), showFilePath, originalFileDivi);

        return "redirect:/votePage";
    }

    @RequestMapping("/items/delete")
    public ResponseEntity<Message> deleteItem(SnackItemForm form) {
        SnackItem one = itemRepository.findOne(form.getId());
        itemService.deleteOne(one.getId());

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();

        message.setStatus(StatusEnum.OK);
        message.setMessage("성공코드");

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("/item/duplicationCheck")
    public ResponseEntity<Message> duplication(SnackItemForm form) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();

        try	{
            SnackItem duplication = itemRepository.duplication(form.getName());
            message.setData("fail");
        } catch (EmptyResultDataAccessException e) {
            message.setData("success");
        }

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("/item/findOneItemInfo")
    public ResponseEntity<?> findOneUser(@RequestParam(name="snack_id") Long snack_id) {

        SnackItem one = itemRepository.findOne(snack_id);

        return ResponseEntity.ok(one);
    }
}
