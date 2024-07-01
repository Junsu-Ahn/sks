package com.example.demo.domain.member.Service;

import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Transactional
    public void join(String username, String password) {
        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();

                memberRepository.save(member);
    }
}
