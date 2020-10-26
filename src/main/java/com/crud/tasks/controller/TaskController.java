package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public TaskDto getTask(Long taskId) {
        return taskMapper.mapToTaskDto(service.findById((long)2).orElse(null));
    }

    @DeleteMapping("deleteTask")
//    @RequestMapping(method = RequestMethod.DELETE, value = "deleteTask")
    public void deleteTask(Long taskId) {

    }

    @PostMapping("updateTask")
//    @RequestMapping(method = RequestMethod.POST, value = "updateTask")
    public TaskDto updateTask(TaskDto task) {
        return new TaskDto(1L, "edited test title", "test content");
    }

    @PostMapping("createTask")
//    @RequestMapping(method = RequestMethod.POST, value = "createTask")
    public void createTask(TaskDto task) {

    }

    @GetMapping("createTask")
//    @RequestMapping(method = RequestMethod.GET, value = "createTask")
    public void getBoard(TaskDto task) {

    }
}
