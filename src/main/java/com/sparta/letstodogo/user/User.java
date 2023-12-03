package com.sparta.letstodogo.user;

import com.sparta.letstodogo.todo.*;
import jakarta.persistence.*;
import java.util.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE) //삭제될 때 Todo도 삭제되도록
//    private List<Todo> todoList;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static User createTestUser(String username, String password) {
        return new User(username, password);
    }
}
