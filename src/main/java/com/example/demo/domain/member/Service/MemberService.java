package com.example.demo.domain.member.Service;

import com.example.demo.domain.member.controller.GlobalException;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.global.rsdata.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokenService authTokenService;

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public boolean passwordMatches(Member member, String password) {
        return passwordEncoder.matches(member.getPassword(), password);
    }

    @Transactional
    public void join(String username, String password) {
        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();

        memberRepository.save(member);
    }
    @AllArgsConstructor
    @Getter
    public static class LoginResponseBody {
        @NotBlank
        private String refreshToken;
        @NotBlank
        private String accessToken;
    }

    @Getter
    @Setter
    public static class LoginRequestBody {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @PostMapping("/login")
    public RsData<LoginResponseBody> login(
            @Valid @RequestBody LoginRequestBody body
    ) {
        RsData<MemberService.AuthAndMakeTokensResponseBody> authAndMakeTokensRs = memberService.authAndMakeTokens(body.getUsername(), body.getPassword());

        return RsData.of(
                authAndMakeTokensRs.getResultCode(),
                authAndMakeTokensRs.getMsg(),
                new LoginResponseBody(
                        authAndMakeTokensRs.getData().getRefreshToken(),
                        authAndMakeTokensRs.getData().getAccessToken()
                )
        );
    }

    @Getter
    public static class AuthAndMakeTokensResponseBody {
        private String accessToken;
        private String refreshToken;

        public AuthAndMakeTokensResponseBody(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }

    @Transactional
    public RsData<AuthAndMakeTokensResponseBody> authAndMakeTokens(String username, String password) {
        Member member = memberService.findByUsername(username)
                .orElseThrow(() -> new GlobalException("400-1", "해당 유저가 존재하지 않습니다."));

        if (!passwordMatches(member, password))
            throw new GlobalException("400-2", "비밀번호가 일치하지 않습니다.");

        String refreshToken = authTokenService.genRefreshToken(member);
        String accessToken = authTokenService.genAccessToken(member);

        return RsData.of(
                "200-1",
                "로그인 성공",
                new AuthAndMakeTokensResponseBody(accessToken, refreshToken)
        );
    }
}
