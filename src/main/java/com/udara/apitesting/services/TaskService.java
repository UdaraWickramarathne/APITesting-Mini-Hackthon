package com.udara.apitesting.services;

import com.udara.apitesting.models.Task;
import com.udara.apitesting.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
        try {
            Task currentTask = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("No task found"));
            currentTask.setTitle(updatedTask.getTitle());
            currentTask.setCompleted(updatedTask.isCompleted());
            return taskRepository.save(currentTask);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteTask(Long id) {
        try {
            Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("No task found"));
            taskRepository.delete(task);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
