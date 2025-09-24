package com.github.enums.controller;

import com.github.enums.scan.Type;

import java.util.List;

/**
 * @author chenxiaoni 2025/9/18 19:34
 */
public class TypeNameResponse {
    private int code;
    private String message;
    private List<Type> types;

    public TypeNameResponse() {
    }

    public TypeNameResponse(List<Type> types) {
        this.code = 0;
        this.message = "Success";
        this.types = types;
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


    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }
}
