package com.sparta.letstodogo.todo;

import com.sparta.letstodogo.user.*;
import java.util.*;
import lombok.*;
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoResponseDto createTodo(TodoRequestDto requestDto, User user) {
//        Todo todo = new Todo(requestDto); //Entity 객체 생성
        Todo todo = Todo.builder()
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .createDate(requestDto.)
            .user(user)
            .build();
        todoRepository.save(todo);
//        return new TodoResponseDto(todo);
        return TodoResponseDto.of(todo);
    }

    public TodoResponseDto getTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
            .orElseThrow(() -> new IllegalArgumentException("해당 할 일은 존재하지 않는다구요!"));
        return TodoResponseDto.of(todo);
    }
}
