package com.example.demo.domain;

import com.example.demo.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ApiV1ArticleController {
    @GetMapping("")
    public List<Article> getArticles() {
        List<Article> articles  =  new ArrayList<>() {{
            add(new Article((1L)));
            add(new Article((2L)));
            add(new Article((3L)));
        }};

        return articles;
    }
}