package com.bookdream.sbb.user;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
public class UserController {

	@Value("${cos.key}")
	private String cosKey;
	
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private KakaoUserService kakaoUserService;
    
    @GetMapping("/user/signup")
    public String signupForm(UserCreateForm userCreateForm) {
        return "user/signupform";
    }

    @PostMapping("/user/signup")
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

    @GetMapping("/user/login")
    public String loginForm() {
        return "user/loginform";
    }
    
    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(@RequestParam("code") String code) { // Data를 리턴해주는 컨트롤러 함수
    	
		RestTemplate rt = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
    	// HttpBody 오브젝트 생성
    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    	params.add("grant_type", "authorization_code");
    	params.add("client_id", "3192757b80d5d97c7263b2166d1afd23");
    	params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
    	params.add("code", code);
    	
    	// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
    	HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
    	
    	// Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음
    	ResponseEntity<String> response = rt.exchange(
    			"https://kauth.kakao.com/oauth/token",
    			HttpMethod.POST,
    			kakaoTokenRequest,
    			String.class
    		);
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	OAuthToken oauthToken = null;
    	try {
    		oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	
    	System.out.println("카카오 엑세스 토큰: " + oauthToken.getAccess_token());
		
    	//==================================================//
    	
    	RestTemplate rt2 = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
    	HttpHeaders headers2 = new HttpHeaders();
    	headers2.add("Authorization","Bearer "+oauthToken.getAccess_token());
    	headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
    	// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
    	HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);
    	
    	// Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음
    	ResponseEntity<String> response2 = rt2.exchange(
    			"https://kapi.kakao.com/v2/user/me",
    			HttpMethod.POST,
    			kakaoProfileRequest2,
    			String.class
    		);
    	System.out.println(response2.getBody());
    	
    	//==================================================//
    	
    	ObjectMapper objectMapper2 = new ObjectMapper();
    	KakaoProfile kakaoProfile = null;
    	try {
    		kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	
//    	System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
//    	System.out.println("카카오 아이디(번호) : " + kakaoProfile.getKakao_account().getEmail());
//    	System.out.println("블로그 서버 유저네임 : " + kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
//    	System.out.println("블로그 서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());
//    	System.out.println("카카오 유저 이름 : " + kakaoProfile.getProperties().getNickname());
//    	System.out.println("블로그 서버 패스워드 : " + cosKey);
    	
    	KakaoUser kakaoUser = KakaoUser.builder()
    			.username(kakaoProfile.getProperties().getNickname())
    			.password(cosKey)
    			.email(kakaoProfile.getKakao_account().getEmail())
    			.build();
    	
    	// 가입자 혹은 비가입자 체크 해서 처리
//    	System.out.println(kakaoUser.getUsername());
//    	System.out.println(kakaoUser.getEmail());
    	KakaoUser originUser = kakaoUserService.findKakaoUser(kakaoUser.getEmail());
    	
    	if(originUser.getUsername() == null) {
    		System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다.============================");
    		kakaoUserService.createKakaoUser(kakaoUser);
    		System.out.println("회원가입 완료!============================");
    	}
    	
    	// 로그인 처리
    	System.out.println("자동 로그인을 진행합니다.============================");
    	try {
    	    Authentication authentication = authenticationManager.authenticate(
    	        new UsernamePasswordAuthenticationToken(kakaoUser.getEmail(), kakaoUser.getPassword())
    	    );
    	    SecurityContextHolder.getContext().setAuthentication(authentication);
    	    return "redirect:/";
    	} catch (AuthenticationException e) {
    	    e.printStackTrace();
    	    System.out.println("왜안됨?????????????????????????????????????????????");
    	    System.out.println("인증 실패: " + e.getMessage());
    	    return "redirect:/user/login";
    	}

    }
   
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/modifypwform")
    public String modifypwform(UserModifyPwForm userModifyForm) {
        return "user/modifypwform";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/user/modifypwform")
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
    @GetMapping("/user/modifynameform")
    public String modifynameform(UserModifyNameForm userModifyNameForm) {
    	return "user/modifynameform";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/user/modifynameform")
    public String modifynameform(@Valid UserModifyNameForm userModifyNameForm, BindingResult bindingResult, Principal principal, Model model) {
        SiteUser user = this.userService.getUser(principal.getName());
        
        if (bindingResult.hasErrors()) {
            return "user/modifynameform";
        }
        
        if (!this.userService.isSamePassword(user, userModifyNameForm.getCurrentPassword())) {
            bindingResult.rejectValue("currentPassword", "notCurrentPassword", "현재 비밀번호와 일치하지 않습니다.");
            return "user/modifynameform";
        }
        
        if (user.getLastNameChangeDate() != null) {
        	if (LocalDateTime.now().isBefore(user.getLastNameChangeDate().plusDays(14))) {
        		bindingResult.rejectValue("nameChangeLimit","beforeNameChangeLimit", "이름을 변경한 지 14일이 지나지 않았습니다.");
        		return "user/modifynameform";
        	}
        }
        
        if (userModifyNameForm.getNewName().equals(user.getUsername())) {
            bindingResult.rejectValue("newName", "sameAsCurrentName", "현재 이름과 같습니다.");
            return "user/modifynameform";
        }
        	
        try {
            userService.modifyName(user, userModifyNameForm.getNewName());
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("modifyNameFailed", e.getMessage());
            return "user/modifynameform";
        }
        
        return "redirect:/";
    }

    



    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/userdel")
    public String userdel(UserDelForm userDelForm) {
        return "user/userdel";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/user/userdel")
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

    
    
    
    @GetMapping("/user/userbuy")
    public String userbuy() {
        return "user/userbuy";
    }
    

}