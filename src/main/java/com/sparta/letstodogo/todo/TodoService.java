package com.sparta.letstodogo.todo;

import com.sparta.letstodogo.user.*;
import java.util.*;
import lombok.*;
import org.springframework.data.domain.*;
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

    public Map<UserDto, List<TodoResponseDto>> getTodoMap() {
        Map<UserDto, List<TodoResponseDto>> userTodoMap = new HashMap<>();
        List<Todo> todoList = todoRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
        todoList.stream().forEach(todo -> {
            var userDto = new UserDto(todo.getUser().getUsername());
            var todoDto = new TodoResponseDto(todo);
            if (userTodoMap.containsKey(userDto)) {
                //유저 할 일 목록 항목을 추가
                userTodoMap.get(userDto).add(todoDto);
            } else {
                //유저 할 일 목록 새로 추가
                userTodoMap.put(userDto, new ArrayList<>(List.of(todoDto)));
            }
        });
        return userTodoMap;
    }
}
