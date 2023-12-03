package com.sparta.letstodogo.todo;

import com.sparta.letstodogo.*;
import com.sparta.letstodogo.user.*;
import java.util.*;
import java.util.concurrent.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto requestDto,
        @AuthenticationPrincipal
        UserDetailsImpl userDetails) {
        TodoResponseDto responseDto = todoService.createTodo(requestDto, userDetails.getUser());
        return ResponseEntity.status(201).body(responseDto);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<CommonResponseDto> getTodo(@PathVariable Long todoId) {
        try {
            TodoResponseDto responseDto = todoService.getTodo(todoId);
            return ResponseEntity.status(200).body(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @GetMapping
    public ResponseEntity<List<TodoListResponseDto>> getTodoList() {
        List<TodoListResponseDto> response = new ArrayList<>();
        Map<UserDto, List<TodoResponseDto>> responseDtoMap = todoService.getTodoMap();
        responseDtoMap.forEach((key, value) -> response.add(new TodoListResponseDto(key, value)));
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<CommonResponseDto> modifyTodo(@PathVariable(name = "todoId") Long todoId,
        @RequestBody TodoRequestDto requestDto,
        @AuthenticationPrincipal
        UserDetailsImpl userDetails) {
        try {
            TodoResponseDto responseDto = todoService.modifyTodo(todoId, requestDto,
                userDetails.getUser());
            return ResponseEntity.ok().body(responseDto);
        } catch (RejectedExecutionException | IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PatchMapping("/{todoId}/complete")
    public ResponseEntity<CommonResponseDto> completeTodo(@PathVariable Long todoId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            TodoResponseDto responseDto = todoService.toggleTodo(todoId,
                userDetails.getUser());
            return ResponseEntity.ok().body(responseDto);
        } catch (RejectedExecutionException | IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
