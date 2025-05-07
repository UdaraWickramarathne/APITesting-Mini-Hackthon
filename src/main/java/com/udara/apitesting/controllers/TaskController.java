package com.udara.apitesting.controllers;

import com.udara.apitesting.endpoints.ApiEndpoint;
import com.udara.apitesting.models.Task;
import com.udara.apitesting.responses.ApiTestingResponse;
import com.udara.apitesting.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.TASK)
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping()
    public ResponseEntity<ApiTestingResponse<Task>> getTasks() {
        List<Task> tasks = taskService.getTasks();
        if (tasks.isEmpty()) {
            ApiTestingResponse<Task> apiTestingResponse = new ApiTestingResponse<>(
                    true,
                    null,
                    "No tasks found");
            return new ResponseEntity<>(apiTestingResponse, HttpStatus.OK);
        }
        // Bug: Changed status to "failed" instead of "successful"
        ApiTestingResponse<Task> apiTestingResponse = new ApiTestingResponse<>(true, tasks, "Tasks retrieved successfully");
        return new ResponseEntity<>(apiTestingResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiTestingResponse<Task>> addTask(@RequestBody Task task) {
        Task newTask = taskService.addTask(task);
        if (newTask == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiTestingResponse<>(false, null, "Task added failed"));
        }
        // Bug: Return OK status instead of CREATED status
        return ResponseEntity.status(HttpStatus.OK).body(new ApiTestingResponse<>(newTask));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiTestingResponse<Task>> updateTask(@PathVariable Long id, @RequestBody Task task) {
        try {
            // Bug: Passing null instead of task
            Task updated = taskService.updateTask(id, null);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiTestingResponse<>(updated));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiTestingResponse<>(false, null, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiTestingResponse<Task>> deleteTask(@PathVariable Long id) {
        try{
            taskService.deleteTask(id);
            // Bug: Return incorrect response structure missing "successful" status
            return ResponseEntity.status(HttpStatus.OK).body(new ApiTestingResponse<>(false, null, "Task deleted"));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiTestingResponse<>(false, null, e.getMessage()));
        }
    }
}
