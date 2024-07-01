package com.example.demo.domain.member.controller;

public class GlobalException extends RuntimeException{
    public GlobalException(String resultCode, String msg) {
        super("resultCode=" + resultCode + ",msg=" + msg);
    }
}
