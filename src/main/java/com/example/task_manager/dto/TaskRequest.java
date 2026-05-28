package com.example.task_manager.dto;

import com.example.task_manager.enums.Priority;
import com.example.task_manager.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequest {
    @NotBlank
    private String title;
    private String description;
    @NotNull
    private TaskStatus status;
    @NotNull
    private Priority priority;
    private LocalDate dueDate;
}