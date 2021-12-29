package jpabook.jpashop.controller;

import lombok.Data;

@Data
public class Message {

    private StatusEnum status;
    private String message;
    private Object data;

    public Message() {
        this.status = StatusEnum.NOT_FOUND;
        this.message = message;
        this.data = data;
    }
}
