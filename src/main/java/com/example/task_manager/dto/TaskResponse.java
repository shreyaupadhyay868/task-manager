package com.example.task_manager.dto;

import com.example.task_manager.enums.Priority;
import com.example.task_manager.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private LocalDate dueDate;
    private String ownerEmail;
}