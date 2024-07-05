package com.bookdream.sbb.user;

import java.util.HashMap;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoAPI kakaoApi;
    private final UserService userService;

    @RequestMapping(value = "/login")
    public String login(@RequestParam("code") String code, HttpSession session) {
        // 1번 인증코드 요청 전달
        String accessToken = kakaoApi.getAccessToken(code);
        // 2번 인증코드로 토큰 전달
        HashMap<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

        System.out.println("login info : " + userInfo.toString());

        if (userInfo.get("email") != null) {
            String email = (String) userInfo.get("email");
            String nickname = (String) userInfo.get("nickname");

            // 사용자 정보를 DB에서 확인하거나 새로 생성합니다.
            SiteUser user = userService.getUserByEmail(email);
            if (user == null) {
                user = userService.createKakaoUser(email, nickname);
            }

            // 세션에 사용자 정보 저장
            session.setAttribute("userId", user.getEmail());
            session.setAttribute("accessToken", accessToken);

            // 디버깅용 로그
            System.out.println("User ID in session: " + session.getAttribute("userId"));
            System.out.println("Access Token in session: " + session.getAttribute("accessToken"));
        } else {
            System.out.println("Email not found in userInfo.");
        }

        return "layout";  // 뷰 이름을 반환
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        kakaoApi.kakaoLogout((String) session.getAttribute("accessToken"));
        session.removeAttribute("accessToken");
        session.removeAttribute("userId");
        return "layout";  // 뷰 이름을 반환
    }
}
