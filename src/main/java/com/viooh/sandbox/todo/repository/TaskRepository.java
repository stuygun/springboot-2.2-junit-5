package com.viooh.sandbox.todo.repository;

import com.viooh.sandbox.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findOneById(Long id);
    Optional<Task> findOneByTitle(String title);
}
