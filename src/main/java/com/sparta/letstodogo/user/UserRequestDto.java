package com.sparta.letstodogo.user;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@AllArgsConstructor
public class UserRequestDto {

    @Pattern(regexp = "^[0-9a-zA-Z]{4,15}$", message = "유저의 이름은 영문자와 숫자 중 4~15 글자로 입력해주세요.")
    private String username;
    @Pattern(regexp = "^[0-9a-zA-Z]{8,30}$", message = "비밀번호는 영문자와 숫자 중 8~30 글자로 입력해주세요.")
    private String password;


}
