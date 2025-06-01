package com.example.task_manager.service;

import com.example.task_manager.model.Task;
import com.example.task_manager.repository.TaskRepository;
import com.example.task_manager.exception.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    
    private final TaskRepository taskRepository;
    
    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    @Override
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
    
    @Override
    public Task createTask(Task task) {
        // Set default status if not provided
        if (task.getStatus() == null) {
            task.setStatus(Task.TaskStatus.TODO);
        }
        
        return taskRepository.save(task);
    }
    
    @Override
    public Task updateTask(Long id, Task taskDetails) {
        return taskRepository.findById(id)
                .map(existingTask -> {
                    // Update task properties
                    if (taskDetails.getTitle() != null) {
                        existingTask.setTitle(taskDetails.getTitle());
                    }
                    if (taskDetails.getDescription() != null) {
                        existingTask.setDescription(taskDetails.getDescription());
                    }
                    if (taskDetails.getStatus() != null) {
                        existingTask.setStatus(taskDetails.getStatus());
                    }
                    if (taskDetails.getDueDate() != null) {
                        existingTask.setDueDate(taskDetails.getDueDate());
                    }
                    
                    // Save updated task
                    return taskRepository.save(existingTask);
                })
                .orElseThrow(() -> new TaskNotFoundException(id));
    }
    
    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }
}
