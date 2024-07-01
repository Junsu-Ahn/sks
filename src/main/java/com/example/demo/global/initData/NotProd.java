package com.example.demo.global.initData;

import com.example.demo.domain.member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class NotProd {
    private final MemberService memberService;

    @Bean
    ApplicationRunner initNotProd() {
        return args -> {
            memberService.join("admin", "1234");
            memberService.join("user1", "1234");
            memberService.join("user2", "1234");
        };
    }
}
