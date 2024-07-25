package com.bookdream.sbb.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModifyPwForm {

    @NotEmpty(message = "새 비밀번호는 필수항목입니다.")
    private String newPassword1;
    
    @NotEmpty(message = "새 비밀번호 확인은 필수항목입니다.")
    private String newPassword2;
    
    @NotEmpty(message = "현재 비밀번호는 필수항목입니다.")
    private String currentPassword;
}