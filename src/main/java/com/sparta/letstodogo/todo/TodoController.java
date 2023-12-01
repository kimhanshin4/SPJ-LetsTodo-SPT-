package com.sparta.letstodogo.todo;

import com.sparta.letstodogo.*;
import com.sparta.letstodogo.user.*;
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
    public ResponseEntity<Void> getTodoList() {
        return ResponseEntity.ok().build();
    }
}
