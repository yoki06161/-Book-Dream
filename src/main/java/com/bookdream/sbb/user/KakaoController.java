package com.bookdream.sbb.user;

import java.util.HashMap;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoAPI kakaoApi;
    private final KakaoUserService kakaoUserService;
    private final UserDetailsService userDetailsService;

    @RequestMapping(value = "/login")
    public String login(@RequestParam("code") String code, HttpSession session) {
        // 1번 인증코드 요청 전달
        String accessToken = kakaoApi.getAccessToken(code);
        // 2번 인증코드로 토큰 전달
        HashMap<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

        if (userInfo.get("email") != null) {
            String email = (String) userInfo.get("email");
            String nickname = (String) userInfo.get("nickname");

            // 사용자 정보를 DB에서 확인하거나 새로 생성합니다.
            KakaoUser user = kakaoUserService.getUserByEmail(email);
            if (user == null) {
                user = kakaoUserService.createKakaoUser(email, nickname);
            }

            // Spring Security 세션에 사용자 정보 저장
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 세션에 사용자 정보 저장
            session.setAttribute("userId", user.getEmail());
            session.setAttribute("accessToken", accessToken);
            

            // 리디렉션을 통해 메인 페이지로 이동
            return "redirect:/";
        } else {
            // 로그인 실패 처리
            return "redirect:/login?error";
        }
    }

    // 로그아웃 처리
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        kakaoApi.kakaoLogout((String) session.getAttribute("accessToken"));
        session.removeAttribute("accessToken");
        session.removeAttribute("userId");
        SecurityContextHolder.clearContext(); // Spring Security 세션 초기화
        return "redirect:/";  // 로그아웃 후 메인 페이지로 리디렉션
    }
}