package com.sparta.letstodogo.domain.user;

import static org.mockito.BDDMockito.*;

import com.sparta.letstodogo.user.*;
import com.sparta.letstodogo.util.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.security.crypto.password.*;

@DisplayName("사용자 @Service Test")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtUtil jwtUtil;

//    @Test - 미완
//    @DisplayName("회원가입")
//    void signup() {
//        //Given
//        String username = "SamPorter";
//        String password = "ReadyToDeliverAPackage";
//        
//        UserRequestDto requestDto = new UserRequestDto(username,password);
//        User user = User.createUser(username,password);
//        
//        //When
//        //Then
//    }
}
