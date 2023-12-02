package com.sparta.letstodogo.todo;

import com.sparta.letstodogo.user.*;
import java.util.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoListResponseDto {

    private UserDto username;
    private List<TodoResponseDto> todoList;

}
