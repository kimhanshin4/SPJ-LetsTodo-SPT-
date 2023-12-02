package com.sparta.letstodogo.todo;

import com.sparta.letstodogo.user.*;
import com.sparta.letstodogo.util.*;
import jakarta.persistence.*;
import java.time.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Todo")
public class Todo extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String content;
//    @Column
//    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; //Todo와 사용자는 N:1

    public Todo(TodoRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
//        this.createDate = LocalDateTime.now();
    }

    public void setUser(User user) {
        this.user = user;
//        user.getTodoList().add(this);
    }
}
