package com.sparta.letstodogo.todo;

import com.sparta.letstodogo.*;
import java.time.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class TodoResponseDto extends CommonResponseDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;

//    public TodoResponseDto(Todo todo) {
//        this.id = todo.getId();
//        this.title = todo.getTitle();
//        this.content = todo.getContent();
//    }

    public static TodoResponseDto of(Todo todo) {
        return TodoResponseDto.builder()
            .id(todo.getId())
            .title(todo.getTitle())
            .content(todo.getContent())
            .createDate(todo.getCreateDate())
            .build();
    }
}
