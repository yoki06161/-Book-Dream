//package com.bookdream.sbb.user;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.client.RestTemplate;
//
//import lombok.RequiredArgsConstructor;
//
//@Controller
//@RequiredArgsConstructor
//public class KakaoController {
//	
//	@GetMapping("/auth/kakao/callback")
//    public @ResponseBody String kakaoCallback(String code) { // Data를 리턴해주는 컨트롤러 함수
//    	
//		RestTemplate rt = new RestTemplate();
//		
//		// HttpHeader 오브젝트 생성
//    	HttpHeaders headers = new HttpHeaders();
//    	headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//		
//    	// HttpBody 오브젝트 생성
//    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//    	params.add("grant_type", "authorization_code");
//    	params.add("client_id", "3192757b80d5d97c7263b2166d1afd23");
//    	params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
//    	params.add("code", code);
//    	
//    	// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
//    	HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
//    	
//    	// Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음
//    	ResponseEntity<String> response = rt.exchange(
//    			"https://kauth.kakao.com/oauth/token",
//    			HttpMethod.POST,
//    			kakaoTokenRequest,
//    			String.class
//    		);
//    	
//    	
//		return "카카오 인증 완료:토큰요청에 대한 응답:" + response;
//    }
//}