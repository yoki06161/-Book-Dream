package com.bookdream.sbb.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModifyNameForm {

    @NotEmpty(message = "현재 비밀번호는 필수항목입니다.")
    private String currentPassword;
    
    @NotEmpty(message = "이름은 필수항목입니다.")
    private String newName;
}
