package com.example.task_manager.repository;

import com.example.task_manager.model.Task;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryTaskRepository implements TaskRepository {
    
    private final Map<Long, Task> taskMap = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    
    @Override
    public List<Task> findAll() {
        return new ArrayList<>(taskMap.values());
    }
    
    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(taskMap.get(id));
    }
    
    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            // New task
            task.setId(idCounter.getAndIncrement());
            task.setCreatedAt(LocalDateTime.now());
        }
        
        task.setUpdatedAt(LocalDateTime.now());
        taskMap.put(task.getId(), task);
        return task;
    }
    
    @Override
    public void deleteById(Long id) {
        taskMap.remove(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return taskMap.containsKey(id);
    }
}
