package com.sparta.letstodogo.todo;

import org.springframework.data.jpa.repository.*;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
