package com.sparta.letstodogo.user;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.*;
import com.sparta.letstodogo.config.*;
import com.sparta.letstodogo.security.*;
import com.sparta.letstodogo.todo.*;
import com.sparta.letstodogo.util.*;
import java.nio.charset.*;
import java.security.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.security.oauth2.resource.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.mapping.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.web.context.*;

@DisplayName("사용자 Controller Test")
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(
    controllers = {UserController.class},
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfig.class
        )
    }
)
class UserControllerTest {

    private MockMvc mvc;

    private Principal mockPrincipal; //인증용

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @MockBean
    JwtUtil jwtUtil;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter()))
            .alwaysDo(print())
            .build();
    }


    @Test
    @DisplayName("회원가입 성공")
    void signup() throws Exception {
        //Given
        String username = "Elsa";
        String password = "DoYouWannaBuildASnoman";
        UserRequestDto userRequestDto = new UserRequestDto(username, password);

        String jsonString = objectMapper.writeValueAsString(userRequestDto);

        //When - Then
        mvc.perform(post("/api/users/signup")
                .content(jsonString)
                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
        //status(HttpStatus.CREATED.value())
    }

    @Test
    @DisplayName("로그인 성공")
    void login() throws Exception {
        //Given
        String username = "Elsa";
        String password = "DoYouWannaBuildASnoman";
        UserRequestDto userRequestDto = new UserRequestDto(username, password);

        String jsonString = objectMapper.writeValueAsString(userRequestDto);

        User testUser = User.createTestUser(username, password);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "",
            testUserDetails.getAuthorities());

//        given(userService.login(userRequestDto)).willReturn("token");

        //When - Then
        MvcResult mvcResult = mvc.perform(post("/api/users/login")
                .content(jsonString)
                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
            )
            .andExpect(status().isOk())
            .andReturn();
        String jwtToken = mvcResult.getResponse().getHeader(JwtUtil.AUTHORIZATION_HEADER);
    }

}