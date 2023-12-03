package com.sparta.letstodogo.todo;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TodoRequestDto {

    private String title;
    private String content;
}