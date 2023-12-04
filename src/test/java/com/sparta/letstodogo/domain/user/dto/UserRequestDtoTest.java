package com.sparta.letstodogo.domain.user.dto;

import static org.assertj.core.api.Assertions.*;

import com.sparta.letstodogo.user.*;
import jakarta.validation.*;
import java.util.*;
import org.junit.jupiter.api.*;

@DisplayName("회원가입&로그인 요청 DTO Test")
public class UserRequestDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    @DisplayName("username&password 요청")
    void validateRequest() {
        //Given
        String username = "Elsa";
        String password = "DoYouWannaBuildASnowman";

        //When
        UserRequestDto requestDto = new UserRequestDto(username, password);

        //Then
        assertThat(requestDto.getUsername()).isEqualTo(username);
        assertThat(requestDto.getPassword()).isEqualTo(password);
    }

    @Test
    @DisplayName("유효하지 않은 username&password 요청")
    void invalidRequest() {
        //Given
        String username = "Els";
        String password = "DoYou";
        UserRequestDto requestDto = new UserRequestDto(username, password);

        //When
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(requestDto);

        //Then
        assertThat(violations.size()).isEqualTo(2);
    }
}


