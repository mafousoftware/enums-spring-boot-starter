package com.winteamiot.enums.controller;

import com.winteamiot.enums.scan.Item;

import java.util.List;

/**
 * @author chenxiaoni 2025/9/18 19:34
 */
public class ItemResponse {
    private int code;
    private String message;
    private List<Item> items;

    public ItemResponse(){}
    public ItemResponse(List<Item> items) {
        this.code = 0;
        this.message = "Success";
        this.items = items;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
