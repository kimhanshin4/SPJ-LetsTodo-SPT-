package com.sparta.letstodogo.todo;

import com.sparta.letstodogo.*;
import com.sparta.letstodogo.user.*;
import java.time.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoResponseDto extends CommonResponseDto {

    private Long id;
    private String username;
    private String title;
    private String content;
    private boolean isComplete;
    private LocalDateTime createDate;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.username = todo.getUser().getUsername();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.isComplete = todo.isCompleted();
        this.createDate = todo.getCreateDate();
    }

//    public TodoResponseDto(Todo todo) {
//        this.id = todo.getId();
//        this.title = todo.getTitle();
//        this.content = todo.getContent();
//    }

    public static TodoResponseDto of(Todo todo) {
        return TodoResponseDto.builder()
            .id(todo.getId())
            .title(todo.getTitle())
            .username(todo.getUser().getUsername())
            .content(todo.getContent())
            .createDate(todo.getCreateDate())
            .build();
    }
}
