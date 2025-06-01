package com.example.task_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private Long id;
    
    private String title;
    
    private String description;
    
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;
    
    // Enum for task status
    public enum TaskStatus {
        TODO,
        IN_PROGRESS,
        COMPLETED
    }
}
