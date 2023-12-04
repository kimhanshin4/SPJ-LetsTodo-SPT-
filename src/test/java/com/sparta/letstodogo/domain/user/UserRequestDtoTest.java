package com.sparta.letstodogo.domain.user;

import static org.assertj.core.api.Assertions.*;

import com.sparta.letstodogo.domain.common.*;
import com.sparta.letstodogo.user.*;
import jakarta.validation.*;
import java.util.*;
import org.junit.jupiter.api.*;

class UserRequestDtoTest implements CommonTest {

//    private Validator validator;

    @DisplayName("사용자 요청 DTO 생성")
    @Nested
    class createUserRequestDto {

        @DisplayName("사용자 요청 DTO 생성 - 성공")
        @Test
        void createUserRequestDto_success() {
            //Given
            UserRequestDto requestDto = new UserRequestDto(TEST_USER_NAME1, TEST_USER_PASSWORD1);

            //When
            Set<ConstraintViolation<UserRequestDto>> violations = validate(requestDto);

            //Then
            assertThat(violations).isEmpty();
        }

        @DisplayName("사용자 요청 DTO 생성 - 실패(유효하지 않은 username)")
        @Test
        void creatUserRequestDto_fail_invalidUsername() {
            //Given
            String username = "El";
            UserRequestDto requestDto = new UserRequestDto(username, TEST_USER_PASSWORD1);

            //When
            Set<ConstraintViolation<UserRequestDto>> violations = validate(requestDto);

            //Then
            assertThat(violations).hasSize(1);
            assertThat(violations).extracting("message")
                .contains("유저의 이름은 영문자와 숫자 중 4~15 글자로 입력해주세요.");
        }

        @DisplayName("사용자 요청 DTO 생성 - 실패(유효하지 않은 password)")
        @Test
        void creatUserRequestDto_fail_invalidPassword() {
            //Given
            String password = "Deliver";
            UserRequestDto requestDto = new UserRequestDto(TEST_USER_NAME2, password);

            //When
            Set<ConstraintViolation<UserRequestDto>> violations = validate(requestDto);

            //Then
            assertThat(violations).hasSize(1);
            assertThat(violations).extracting("message")
                .contains("비밀번호는 영문자와 숫자 중 8~30 글자로 입력해주세요.");
        }

    }

    //@Valid 역할
    private Set<ConstraintViolation<UserRequestDto>> validate(UserRequestDto requestDto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(requestDto);
    }

}
