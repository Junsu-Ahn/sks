package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Article {
    private Long id;

    public Article(long l) {
        this.id = l;
    }
}