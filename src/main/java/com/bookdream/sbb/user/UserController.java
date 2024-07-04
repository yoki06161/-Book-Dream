//package com.bookdream.sbb.user;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
//import jakarta.servlet.http.HttpSession;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//
//@Controller
//@RequestMapping("/user")
//@RequiredArgsConstructor
//public class UserController {
//	
//	private final UserService userService;
//	
//	@GetMapping("/signup")
//	public String signup(UserCreateForm userCreateForm) {
//		return "user/signupform";
//	}
//	
//	@PostMapping("/signup")
//	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
//		if(bindingResult.hasErrors()) {
//			return "user/signupform";
//		}
//		
//		if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())){
//			bindingResult.rejectValue("password2", "passwordIncorrect", "패스워드가 다릅니다.");
//			return "user/signupform";
//		}
//		
//		Map<String, String> map = new HashMap<>();
//		map.put("username", userCreateForm.getUsername());
//		map.put("password", userCreateForm.getPassword1());
//		map.put("email", userCreateForm.getEmail());
//		
//		try {
//			userService.create(map);	
//		} catch (DataIntegrityViolationException e) {
//			e.printStackTrace();
//			bindingResult.reject("signupFailed", "이미 등록된 사용자 입니다.");
//			return "user/signupform";
//		}catch (Exception e) {
//			e.printStackTrace();
//			bindingResult.reject("signupFailed", e.getMessage());
//			return "user/signupform";
//		}
//		
//		
//		return "redirect:/";
//		
//	}
//	
//	@GetMapping("/login")
//	public String signup() {
//		return "user/loginform";
//	}
//	
//
//	KakaoAPI kakaoApi = new KakaoAPI();
//	
//	@RequestMapping(value="/login")
//	public ModelAndView login(@RequestParam("code") String code, HttpSession session) {
//		ModelAndView mav = new ModelAndView();
//		// 1번 인증코드 요청 전달
//		String accessToken = kakaoApi.getAccessToken(code);
//		// 2번 인증코드로 토큰 전달
//		HashMap<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);
//		
//		System.out.println("login info : " + userInfo.toString());
//		
//		if(userInfo.get("email") != null) {
//			session.setAttribute("userId", userInfo.get("email"));
//			session.setAttribute("accessToken", accessToken);
//		}
//		mav.addObject("userId", userInfo.get("email"));
//		mav.setViewName("loginform");
//		return mav;
//	}
//	
//	@RequestMapping(value="/logout")
//	public ModelAndView logout(HttpSession session) {
//		ModelAndView mav = new ModelAndView();
//		
//		kakaoApi.kakaoLogout((String)session.getAttribute("access_token"));
//		session.removeAttribute("accessToken");
//		session.removeAttribute("userId");
//		mav.setViewName("index");
//		return mav;
//	}
//	
//	
//}
//
//
//
//
