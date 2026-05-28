package com.example.task_manager.service;

import com.example.task_manager.dto.TaskRequest;
import com.example.task_manager.dto.TaskResponse;
import com.example.task_manager.entity.Task;
import com.example.task_manager.entity.User;
import com.example.task_manager.enums.Priority;
import com.example.task_manager.enums.TaskStatus;
import com.example.task_manager.repository.TaskRepository;
import com.example.task_manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private TaskResponse toResponse(Task task) {
        TaskResponse r = new TaskResponse();
        r.setId(task.getId());
        r.setTitle(task.getTitle());
        r.setDescription(task.getDescription());
        r.setStatus(task.getStatus());
        r.setPriority(task.getPriority());
        r.setDueDate(task.getDueDate());
        r.setOwnerEmail(task.getOwner().getEmail());
        return r;
    }

    public TaskResponse createTask(TaskRequest request) {
        User user = getCurrentUser();
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .priority(request.getPriority())
                .dueDate(request.getDueDate())
                .owner(user)
                .build();
        return toResponse(taskRepository.save(task));
    }

    public TaskResponse getTask(Long id) {
        User user = getCurrentUser();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!task.getOwner().getId().equals(user.getId()) &&
                !user.getRole().name().equals("ADMIN")) {
            throw new RuntimeException("Access denied");
        }
        return toResponse(task);
    }

    public Page<TaskResponse> getTasks(TaskStatus status, Priority priority, Pageable pageable) {
        User user = getCurrentUser();
        if (status != null && priority != null) {
            return taskRepository.findByOwnerAndStatusAndPriority(
                    user, status, priority, pageable).map(this::toResponse);
        } else if (status != null) {
            return taskRepository.findByOwnerAndStatus(
                    user, status, pageable).map(this::toResponse);
        } else if (priority != null) {
            return taskRepository.findByOwnerAndPriority(
                    user, priority, pageable).map(this::toResponse);
        }
        return taskRepository.findByOwner(user, pageable).map(this::toResponse);
    }

    public TaskResponse updateTask(Long id, TaskRequest request) {
        User user = getCurrentUser();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!task.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        return toResponse(taskRepository.save(task));
    }

    public void deleteTask(Long id) {
        User user = getCurrentUser();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!task.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }
        taskRepository.delete(task);
    }

    public boolean isOwner(Long taskId, String email) {
        return taskRepository.findById(taskId)
                .map(t -> t.getOwner().getEmail().equals(email))
                .orElse(false);
    }
}