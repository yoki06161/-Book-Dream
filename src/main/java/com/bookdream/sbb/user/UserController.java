package com.bookdream.sbb.user;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

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
            } else if (e.getMessage().contains("이미 등록된 사용자 이름입니다.")) {
                bindingResult.rejectValue("username", "duplicateUsername", "이미 등록된 사용자 이름입니다.");
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
    @GetMapping("/modify_ch")
    public String modify_ch(Model model) {
        // 현재 인증된 사용자의 이메일 가져오기
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        // 현재 사용자 정보 가져오기
        SiteUser currentUser = userService.getUserByEmail(currentUserEmail);

        // 현재 사용자의 정보를 모델에 추가하여 수정 페이지로 전달
        model.addAttribute("currentUser", currentUser);

        return "user/modify_ch"; // 수정 페이지로 이동
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify_ch")
    public String modify_ch(@Valid UserCreateForm userCreateForm, BindingResult bindingResult,
                         @RequestParam("currentPassword") String currentPassword, HttpSession session) {
        // 현재 인증된 사용자의 이메일 가져오기
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        // 현재 비밀번호 확인
        if (!userService.checkPassword(currentUserEmail, currentPassword)) {
            bindingResult.rejectValue("currentPassword", "incorrectPassword", "현재 비밀번호가 일치하지 않습니다.");
            return "user/modify_ch";
        }
        return "redirect:/user/modify";
    }

    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify")
    public String modify(Principal principal, Map<String, Object> model, UserModifyForm userModifyForm) {
        String currentUserEmail = principal.getName();
        SiteUser user = userService.getUserByEmail(currentUserEmail);

        // 현재 사용자의 이메일과 이름을 모델에 추가하여 화면에 보여줌
        model.put("currentUserEmail", user.getEmail());
        model.put("currentUserName", user.getUsername());

        // 현재 사용자의 이메일과 로그인한 사용자의 이메일이 다를 경우 예외 발생
        if (!user.getEmail().equals(currentUserEmail)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return "user/modify";
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify")
    public String modify(Principal principal, Map<String, Object> model) {
        String currentUserEmail = principal.getName();
        SiteUser user = userService.getUserByEmail(currentUserEmail);

        // 현재 사용자의 이메일과 이름을 모델에 추가하여 화면에 보여줌
        model.put("currentUserEmail", user.getEmail());
        model.put("currentUserName", user.getUsername());

        // 현재 사용자의 이메일과 로그인한 사용자의 이메일이 다를 경우 예외 발생
        if (!user.getEmail().equals(currentUserEmail)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 비밀번호 변경 폼을 초기화
        UserModifyForm userModifyForm = new UserModifyForm();
        model.put("userModifyForm", userModifyForm);

        return "user/modify";
    }



    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public String modify(@Valid UserModifyForm userModifyForm, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes, SiteUser user) {
        if (bindingResult.hasErrors()) {
            return "user/modify";
        }

        String currentUserEmail = principal.getName();
        Map<String, String> modifyMap = new HashMap<>();
        modifyMap.put("password", userModifyForm.getNewPassword1());
        
     // 현재 사용자의 비밀번호를 수정 폼에 설정
        if(userModifyForm.getNewPassword1() == userModifyForm.getNewPassword2()) {
        	userModifyForm.setNewPassword1(user.getPassword());
        }

        try {
            userService.modify(currentUserEmail, modifyMap, user);
            redirectAttributes.addFlashAttribute("successMessage", "비밀번호가 성공적으로 변경되었습니다.");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "비밀번호 변경 중 오류가 발생했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "비밀번호 변경 중 오류가 발생했습니다.");
        }
        

        return "redirect:/user/loginform";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public String modify(@Valid UserModifyForm userModifyForm, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "user/modify";
        }

        String currentUserEmail = principal.getName();
        Map<String, String> modifyMap = new HashMap<>();
        modifyMap.put("password", userModifyForm.getNewPassword1());

        try {
            // 사용자 서비스를 사용하여 비밀번호 변경을 처리
            userService.modify(currentUserEmail, modifyMap);

            // 성공 메시지를 리다이렉트 속성에 추가
            redirectAttributes.addFlashAttribute("successMessage", "비밀번호가 성공적으로 변경되었습니다.");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "비밀번호 변경 중 오류가 발생했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "비밀번호 변경 중 오류가 발생했습니다.");
        }

        return "redirect:/user/modify";
    }


    
    
    @GetMapping("/userdel")
    public String userdel() {
        return "user/userdel";
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
