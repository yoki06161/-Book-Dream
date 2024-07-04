//package com.bookdream.sbb.admin;
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
//
//import com.bookdream.sbb.user.UserCreateForm;
//
//import lombok.RequiredArgsConstructor;
//
//@Controller
//@RequestMapping("/admin")
//@RequiredArgsConstructor
//public class AdminController {
//	
//	private final AdminService adminService;
//	
//	@GetMapping("/signup")
//	public String signup(AdminCreateForm adminCreateForm) {
//		return "signup/form";
//	}
//	
//	@PostMapping("/signup")
//	public String signup(@Valid AdminCreateForm userCreateForm, BindingResult bindingResult)
//		if(bindingResult.hasErrors()) {
//			return "signup/form";
//		}
//	
//	if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
//		bindingResult.rejectValue("password2", "passwordIncorrect", "패스워드가 다릅니다.");
//		return "signup/form";
//	}
//	
//	Map<String, String> map = new HashMap<>();
//	map.put("username", userCreateForm.getUsername());
//	map.put("password", userCreateForm.getPassword1());
//	map.put("email", userCreateForm.getEmail());
//	
//	try {
//		adminService.create(map);
//	} catch (DataIntegrityViolationException e) {
//		e.printStackTrace();
//		bindingResult.reject("signupFailed", "이미 등록된 사용자 입니다.");
//		return "signup/form";
//	} catch (Exception e) {
//		e.printStackTrace();
//		bindingResult.reject("signupFailed", e.getMessage());
//		return "signup/form";
//	}
//	
//	return "redirect:/";
//	
//
//}
//
//@GetMapping("/login")
//public String signup() {
//   return "login/form";
//}
//}