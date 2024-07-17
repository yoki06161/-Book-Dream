package com.bookdream.sbb.user;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	@Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private KakaoUserService kakaoUserService;
    
    @Autowired
    private final PasswordEncoder passwordEncoder;
    
//    @GetMapping(value = {"", "/"})
//    public String home(Model model) {
//        
//        model.addAttribute("loginType", "security-login");
//        model.addAttribute("pageName", "스프링 시큐리티 로그인");
//
//        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
//        GrantedAuthority auth = iter.next();
//        String role = auth.getAuthority();
//
//        SiteUser loginMember = userService.getUserByEmail(loginId);
//
//        if (loginMember != null) {
//            model.addAttribute("name", loginMember.getUsername());
//        }
//
//        return "layout";
//    }
    
    @GetMapping("/signup")
    public String signupForm(UserCreateForm userCreateForm) {
        return "user/signupform";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/signupform";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordIncorrect", "패스워드가 다릅니다.");
            return "/signupform";
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
            return "/signupform";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "/signupform";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        return "user/loginform";
    }

    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modifypwform")
    public String modifypwform(UserModifyPwForm userModifyForm) {
        return "/modifypwform";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modifypwform")
    public String modifypwform(@Valid UserModifyPwForm userModifyPwForm, BindingResult bindingResult, Principal principal, Model model) {
        SiteUser user = this.userService.getUser(principal.getName());
    	
    	if (bindingResult.hasErrors()) {
            return "user/modifypwform";
        }

    	if (!this.userService.isSamePassword(user, userModifyPwForm.getCurrentPassword())) {
            bindingResult.rejectValue("currentPassword", "notCurrentPassword", "현재 비밀번호와 일치하지 않습니다. ");
            return "user/modifypwform";
        }
    	
    	if (!userModifyPwForm.getNewPassword1().equals(userModifyPwForm.getNewPassword2())) {
            bindingResult.rejectValue("newPassword2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "user/modifypwform";
        }
    	
    	try {
            userService.modifyPassword(user, userModifyPwForm.getNewPassword1());
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("modifyPasswordFailed", e.getMessage());
            return "user/modifypwform";
        }

        return "redirect:/";
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modifynameform")
    public String modifynameform(UserModifyNameForm userModifyNameForm) {
    	return "/modifynameform";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modifynameform")
    public String modifynameform(@Valid UserModifyNameForm userModifyNameForm, BindingResult bindingResult, Principal principal, Model model) {
        SiteUser user = this.userService.getUser(principal.getName());
        
        if (bindingResult.hasErrors()) {
            return "/modifynameform";
        }
        
        if (!this.userService.isSamePassword(user, userModifyNameForm.getCurrentPassword())) {
            bindingResult.rejectValue("currentPassword", "notCurrentPassword", "현재 비밀번호와 일치하지 않습니다.");
            return "/modifynameform";
        }
        
        if (user.getLastNameChangeDate() != null) {
        	if (LocalDateTime.now().isBefore(user.getLastNameChangeDate().plusDays(14))) {
        		bindingResult.rejectValue("nameChangeLimit","beforeNameChangeLimit", "이름을 변경한 지 14일이 지나지 않았습니다.");
        		return "/modifynameform";
        	}
        }
        
        if (userModifyNameForm.getNewName().equals(user.getUsername())) {
            bindingResult.rejectValue("newName", "sameAsCurrentName", "현재 이름과 같습니다.");
            return "/modifynameform";
        }
        	
        try {
            userService.modifyName(user, userModifyNameForm.getNewName());
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("modifyNameFailed", e.getMessage());
            return "/modifynameform";
        }
        
        return "redirect:/";
    }

    



    @PreAuthorize("isAuthenticated()")
    @GetMapping("/userdel")
    public String userdel(UserDelForm userDelForm) {
        return "/userdel";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/userdel")
    public String userdel(@Valid UserDelForm userDelForm, BindingResult bindingResult, Principal principal, Model model, HttpSession session) {
        SiteUser user = this.userService.getUser(principal.getName());
        
        if (bindingResult.hasErrors()) {
            return "/userdel";
        }

        if (!this.userService.isSamePassword(user, userDelForm.getCurrentPassword())) {
            bindingResult.rejectValue("currentPassword", "notCurrentPassword", "현재 비밀번호와 일치하지 않습니다.");
            return "/userdel";
        }
        
        try {
            userService.deleteUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("deleteUserFailed", e.getMessage());
            return "/userdel";
        }
        
     // 로그아웃 처리
        session.invalidate(); // 세션 무효화
        SecurityContextHolder.clearContext(); // Spring Security 세션 초기화
        return "redirect:/";
    }

    
    
    
    @GetMapping("/userbuy")
    public String userbuy() {
        return "/userbuy";
    }
    

}
