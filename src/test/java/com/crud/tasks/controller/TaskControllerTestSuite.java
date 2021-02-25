package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTestSuite extends TestCase {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldGetTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "test_title1", "test_content1");
        when(taskMapper.mapToTaskDto(any())).thenReturn(taskDto);
        Task task = new Task(1L, "test_title1", "test_content1");
        Optional<Task> optTask = Optional.of(task);
        when(dbService.findById(anyLong())).thenReturn(optTask);

        //When&Then
        mockMvc.perform(get("/v1/tasks/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test_title1")))
                .andExpect(jsonPath("$.content", is("test_content1")));
    }

    @Test
    public void shouldGetTasksList() throws Exception {
        //Given
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "test_title1", "test_content1"));
        taskList.add(new Task(2L, "test_title1", "test_content1"));
        when(dbService.getAllTasks()).thenReturn(taskList);
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(new TaskDto(1L, "test_title1", "test_content1"));
        taskDtoList.add(new TaskDto(2L, "test_title1", "test_content1"));
        when(taskMapper.mapToTaskDtoList(any())).thenReturn(taskDtoList);

        //When&Then
        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("test_title1")))
                .andExpect(jsonPath("$[0].content", is("test_content1")));
    }

    @Test
    public void shouldGetEmptyTasksList() throws Exception {
        //Given
        when(dbService.getAllTasks()).thenReturn(new ArrayList<>());

        //When&Then
        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        //Given

        //When&Then
        mockMvc.perform(delete("/v1/tasks/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(dbService, times(1)).deleteTaskById(1L);
    }

    @Test
    public void shouldCreateTask() throws Exception {
        //Given
        Gson gson = new Gson();
        Task task = new Task(1L, "test_title1", "test_content1");
        TaskDto taskDto = new TaskDto(1L, "test_title1", "test_content1");
        String jsonContent = gson.toJson(taskDto);
        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);

        //When&Then
        mockMvc.perform(post("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
        verify(dbService, times(1)).saveTask(task);
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        Gson gson = new Gson();
        TaskDto newTaskDto = new TaskDto(1L, "edited_title1", "edited_content1");
        Task newTask = new Task(1L, "edited_title1", "edited_content1");
        String jsonContent = gson.toJson(newTaskDto);
        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(newTask);


        //When&Then
        mockMvc.perform(put("/v1/tasks").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
        verify(dbService, times(1)).saveTask(any(Task.class));
    }
}