package jpabook.jpashop.controller;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class SnackItemForm {
    private Long id;
    private String name;
    private String sellImgUrl;
}
