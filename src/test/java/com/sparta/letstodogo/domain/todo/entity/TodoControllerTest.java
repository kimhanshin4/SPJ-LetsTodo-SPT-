package com.sparta.letstodogo.domain.todo.entity;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.BDDMockito.*;

import com.fasterxml.jackson.databind.*;
import com.sparta.letstodogo.config.*;
import com.sparta.letstodogo.security.*;
import com.sparta.letstodogo.todo.*;
import com.sparta.letstodogo.user.*;
import com.sparta.letstodogo.util.*;
import java.nio.charset.*;
import java.security.*;
import java.util.*;
import java.util.stream.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.mapping.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.test.util.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.web.context.*;

@DisplayName("할일 @Controller Test")
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(
    controllers = {TodoController.class},
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfig.class
        )
    }
)
class TodoControllerTest {

    private MockMvc mvc;

    private Principal mockPrincipal; //인증용

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    UserService userService;

    @MockBean
    TodoService todoService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    TodoRepository todoRepository;


    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter()))
            .alwaysDo(print())
            .build();
        //Test 유저 생성
        User user1 = User.createTestUser("Elsa", "DoYouWannaBuildASnowman");
        UserDetailsImpl testUserDetails = new UserDetailsImpl(user1);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "",
            testUserDetails.getAuthorities());

    }

    @Test
    @DisplayName("할일 생성 Test")
    void createTodo() throws Exception {
        //Given
        User user1 = User.createTestUser("Elsa", "DoYouWannaBuildASnowman");
        UserDto userDto = new UserDto(user1.getUsername());
        TodoRequestDto requestDto = new TodoRequestDto("열심열심", "드가자!스파르탄!");
        Todo todo = Todo.builder()
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .user(user1)
            .build();
        String jsonString = objectMapper.writeValueAsString(requestDto);
        TodoResponseDto responseDto = new TodoResponseDto(todo);

        given(todoService.createTodo(any(TodoRequestDto.class), any(User.class))).willReturn(
            responseDto);
        //When
        ResultActions resultActions = mvc.perform(post("/api/todos")
            .content(jsonString)
            .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
            .accept(MediaType.APPLICATION_JSON)
            .principal(mockPrincipal));

        //Then
        resultActions.andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value(responseDto.getTitle()))
            .andExpect(jsonPath("$.content").value(responseDto.getContent()))
            .andExpect(jsonPath("$.username").value(responseDto.getUsername()));

    }

    @Test
    @DisplayName("할일 수정 Test")
    void modifyTodo() throws Exception {
        User user1 = User.createTestUser("Elsa", "DoYouWannaBuildASnowman");
        UserDetailsImpl testUserDetails = new UserDetailsImpl(user1);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "",
            testUserDetails.getAuthorities());
        Long todoId = 1L;
        //Given
        TodoRequestDto requestDto = new TodoRequestDto("열심열심", "드가자!스파르탄!");
        Todo todo = Todo.builder()
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .user(user1)
            .build();

        TodoResponseDto responseDto = new TodoResponseDto(todo);
        String jsonString = objectMapper.writeValueAsString(requestDto);

        given(todoService.modifyTodo(any(Long.class), any(TodoRequestDto.class),
            any(User.class))).willReturn(
            responseDto);
        //When
        mvc.perform(patch("/api/todos/{todoId}", todoId)
                .content(jsonString)
                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                .accept(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("할일 단일 조회 Test")
    void getTodo() throws Exception {
        //Given
        Long todoId = 1L;
        User user1 = User.createTestUser("Elsa", "DoYouWannaBuildASnowman");
        Todo todo1 = new Todo("title1", "content1", true, user1);

        given(todoRepository.findById(todoId)).willReturn(Optional.of(todo1));
        TodoResponseDto responseDto = new TodoResponseDto(todoRepository.findById(todoId).get());
        given(todoService.getTodo(todoId)).willReturn(responseDto);
        //When
        ResultActions resultActions = mvc.perform(get("/api/todos/{todoId}", todoId)
            .accept(MediaType.APPLICATION_JSON));
        //Then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value(todo1.getTitle()))
            .andExpect(jsonPath("$.content").value(todo1.getContent()))
            .andExpect(jsonPath("$.username").value(todo1.getUser().getUsername()));
    }

}
