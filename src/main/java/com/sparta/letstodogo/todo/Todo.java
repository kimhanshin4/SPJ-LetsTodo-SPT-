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
    @Column
    private boolean isCompleted;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; //Todo와 사용자는 N:1

    public Todo(TodoRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.isCompleted = false;
    }


    public Todo(String title, String content, boolean isCompleted, User user) {
        this.title = title;
        this.content = content;
        this.isCompleted = false;
        this.user = user;
    }

    //연관관계 메서드
    public void setUser(User user) {
        this.user = user;
    }

    //서비스 메서드
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void toggleCompleted() {
        isCompleted = !isCompleted;
    }
}
