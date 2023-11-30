package com.sparta.letstodogo;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
public class UserRequestDto {

    @Pattern(regexp = "^[0-9a-z]{4,10}$", message = "유저의 이름은 영문자와 숫자 중 4~10 글자로 입력해주세요.")
    private String username;
    @Pattern(regexp = "^[0-9a-z]{8,15}$", message = "비밀번호는 영문자와 숫자 중 8~15 글자로 입력해주세요.")
    private String password;


}
