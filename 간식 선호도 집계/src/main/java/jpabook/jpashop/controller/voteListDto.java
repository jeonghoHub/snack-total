package jpabook.jpashop.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class voteListDto {
    private String year;
    private String category;
    private String name;

}

