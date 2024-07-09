package com.bookdream.sbb.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModifyForm {

    @NotEmpty(message = "새 비밀번호는 필수항목입니다.")
    private String newPassword1;
    
    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String newPassword2;
    
    // 필요에 따라 추가적인 필드들을 정의할 수 있습니다.
}
