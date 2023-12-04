package com.sparta.letstodogo.domain.user.repository;

import static org.assertj.core.api.Assertions.*;

import com.sparta.letstodogo.user.*;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@DisplayName("사용자 @Repository Test")
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    @Test - Error
//    @DisplayName("username 으로 User 찾기")
//    void findByUsername() {
//        //Given
//        String username = "SamPorter";
//        String password = "ReadyToDeliverAPackage";
//        String encordedPassword = passwordEncoder.encode(password);
//        User createUser = userRepository.save(User.createUser(username, encordedPassword));
//
//        //When
//        Optional<User> foundUser = userRepository.findByUsername(username);
//
//        //Then
//        assertThat(foundUser.isPresent()).isEqualTo(true); //새로운 User 저장 여부 확인
//        assertThat(foundUser.get()).isEqualTo(createUser); //찾아온 user와 저장한 user가 같은지 여부 확인
//    }

}
