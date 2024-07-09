package com.bookdream.sbb.user;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signupForm(UserCreateForm userCreateForm) {
        return "user/signupform";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/signupform";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordIncorrect", "패스워드가 다릅니다.");
            return "user/signupform";
        }

        Map<String, String> map = new HashMap<>();
        map.put("username", userCreateForm.getUsername());
        map.put("password", userCreateForm.getPassword1());
        map.put("email", userCreateForm.getEmail());

        try {
            userService.create(map);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            if (e.getMessage().contains("이미 등록된 이메일입니다.")) {
                bindingResult.rejectValue("email", "duplicateEmail", "이미 등록된 이메일입니다.");
            } else {
                bindingResult.reject("signupFailed", e.getMessage());
            }
            return "user/signupform";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "user/signupform";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/loginform";
    }
   
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modifyform")
    public String modifyform(UserModifyForm userModifyForm) {
        return "user/modifyform";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modifyform")
    public String modifyform(@Valid UserModifyForm userModifyForm, BindingResult bindingResult, Principal principal, Model model) {
        SiteUser user = this.userService.getUser(principal.getName());
    	
    	if (bindingResult.hasErrors()) {
            return "user/modifyform";
        }

    	if (!this.userService.isSamePassword(user, userModifyForm.getBeforePassword())) {
            bindingResult.rejectValue("beforePassword", "notBeforePassword", "이전 비밀번호와 일치하지 않습니다. ");
            return "user/modifyform";
        }
    	
    	if (!userModifyForm.getNewPassword1().equals(userModifyForm.getNewPassword2())) {
            bindingResult.rejectValue("newPassword2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "user/modifyform";
        }
    	
    	
    	try {
            userService.modifyPassword(user, userModifyForm.getNewPassword1());
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("modifyPasswordFailed", e.getMessage());
            return "user/modifyform";
        }

        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/userdel")
    public String userdel(UserDelForm userDelForm) {
        return "user/userdel";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/userdel")
    public String userdel(@Valid UserDelForm userDelForm, BindingResult bindingResult, Principal principal, Model model, HttpSession session) {
        SiteUser user = this.userService.getUser(principal.getName());
        
        if (bindingResult.hasErrors()) {
            return "user/userdel";
        }

        if (!this.userService.isSamePassword(user, userDelForm.getCurrentPassword())) {
            bindingResult.rejectValue("currentPassword", "notCurrentPassword", "현재 비밀번호와 일치하지 않습니다.");
            return "user/userdel";
        }
        
        try {
            userService.deleteUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("deleteUserFailed", e.getMessage());
            return "user/userdel";
        }
        
     // 로그아웃 처리
        session.invalidate(); // 세션 무효화
        SecurityContextHolder.clearContext(); // Spring Security 세션 초기화
        return "redirect:/";
    }

    
    
    
    @GetMapping("/buy")
    public String buy() {
        return "user/buy";
    }
    @GetMapping("/pay")
    public String pay() {
        return "user/pay";
    }
    

}
