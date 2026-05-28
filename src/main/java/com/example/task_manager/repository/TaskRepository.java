package com.example.task_manager.repository;

import com.example.task_manager.entity.Task;
import com.example.task_manager.entity.User;
import com.example.task_manager.enums.Priority;
import com.example.task_manager.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByOwner(User owner, Pageable pageable);
    Page<Task> findByOwnerAndStatus(User owner, TaskStatus status, Pageable pageable);
    Page<Task> findByOwnerAndPriority(User owner, Priority priority, Pageable pageable);
    Page<Task> findByOwnerAndStatusAndPriority(User owner, TaskStatus status, Priority priority, Pageable pageable);
}