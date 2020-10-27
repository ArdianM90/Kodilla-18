package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/task")
public class TaskController {
    @Autowired
    private DbService service;
    @Autowired
    private TaskMapper taskMapper;

    @GetMapping("getTasks") //TAK
    public List<TaskDto> getTasks() {
        return taskMapper.mapToTaskDtoList(service.getAllTasks());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTask") //ALBO TAK
    public TaskDto getTask(@RequestParam Long taskId) throws TaskNotFoundException {
        return taskMapper.mapToTaskDto(service.findById(taskId).orElseThrow(TaskNotFoundException::new));
    }

    @DeleteMapping("deleteTask")
//    @RequestMapping(method = RequestMethod.DELETE, value = "deleteTask")
    public void deleteTask(@RequestParam Long taskId) {
        service.deleteTask(taskId);
    }

    @PostMapping("updateTask")
//    @RequestMapping(method = RequestMethod.POST, value = "updateTask")
    public TaskDto updateTask(@RequestBody TaskDto task) {
        return new TaskDto(1L, "edited test title", "test content");
    }

    @PostMapping(value = "createTask", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(method = RequestMethod.POST, value = "createTask", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createTask(@RequestBody TaskDto taskDto) {
        service.saveTask(taskMapper.mapToTask(taskDto));
    }

//    @GetMapping("getBoard")
////    @RequestMapping(method = RequestMethod.GET, value = "getBoard")
//    public void getBoard(TaskDto task) {
//
//    }
}
