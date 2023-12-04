package com.sparta.letstodogo.domain.todo.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.sparta.letstodogo.todo.*;
import com.sparta.letstodogo.user.*;
import java.util.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

@DisplayName("할일 @Service Test")
@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    TodoRepository todoRepository;
    @InjectMocks
    TodoService todoService;

    @Test
    @DisplayName("할일 생성 Test")
    void createTodo() {
        //Given
        String username = "SamPorter";
        String password = "ReadyToDeliverAPackage";
        User user = User.createTestUser(username, password);

        String title = "뭐죠?";
        String content = "아주 두근세근하죠?";
        TodoRequestDto requestDto = new TodoRequestDto(title, content);

        Todo todo = Todo.builder()
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .user(user)
            .build();

        given(todoRepository.save(any(Todo.class))).willReturn(todo);

        //When
        TodoResponseDto responseDto = todoService.createTodo(requestDto, user);

        //Then
        assertThat(responseDto.getTitle()).isEqualTo(title);
        assertThat(responseDto.getContent()).isEqualTo(content);
        assertThat(responseDto.getUsername()).isEqualTo(username);
    }

    //    @Test - 미완
//    @DisplayName("할일 수정 Test")
//    void modifyTodo() {
//        //Given
//        String username = "SamPorter";
//        String password = "ReadyToDeliverAPackage";
//        User user = User.createTestUser(username, password);
//
//        Long todoId = 1L;
//        String title = "드가자!";
//        String content = "바로갑니다";
//        TodoRequestDto requestDto = new TodoRequestDto(title, content);
//        Todo todo = Todo.builder()
//            .title(requestDto.getTitle())
//            .content(requestDto.getContent())
//            .user(user)
//            .build();
//        given(todoRepository.save(any(Todo.class))).willReturn(todo);
//
//        String modifiedTitle = "오케";
//        String modifiedContent = "바로가는거죠";
//        requestDto = new TodoRequestDto(title, content);
//        todo = Todo.builder()
//            .id(todoId)
//            .title(requestDto.getTitle())
//            .content(requestDto.getContent())
//            .user(user)
//            .build();
//        given(todoRepository.findById(todoId)).willReturn(Optional.of(todo));
//
//        //When
//        TodoResponseDto responseDto = todoService.modifyTodo(todoId, requestDto, user);
//
//        //Then
//        assertThat(responseDto.getTitle()).isEqualTo(modifiedTitle);
//        assertThat(responseDto.getContent()).isEqualTo(modifiedContent);
//        assertThat(responseDto.getUsername()).isEqualTo(user.getUsername());
//    }
    @Test
    @DisplayName("할일 단일 조회 Test")
    void getTodo() {
        //Given
        String username = "SamPorter";
        String password = "ReadyToDeliverAPackage";
        User user = User.createTestUser(username, password);

        Long todoId = 1L;
        String title = "드가자!";
        String content = "바로갑니다";
        Todo todo = new Todo(title, content, false, user);

        given(todoRepository.findById(todoId)).willReturn(Optional.of(todo));

        //When
        TodoResponseDto responseDto = todoService.getTodo(todoId);

        //Then
        assertThat(responseDto.getTitle()).isEqualTo(title);
        assertThat(responseDto.getContent()).isEqualTo(content);
        assertThat(responseDto.getUsername()).isEqualTo(username);

    }

}
