package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTestSuite extends TestCase {
    @Autowired
    TaskMapper taskMapper;

    @Test
    public void shouldMapTaskToTaskDto() {
        //Given
        Task testTask = new Task(1L, "test", "test description");

        //When
        TaskDto result = taskMapper.mapToTaskDto(testTask);

        //Then
        assertEquals(1L, result.getId(), 0);
        assertEquals("test", result.getTitle());
        assertEquals("test description", result.getContent());
    }

    @Test
    public void shouldMapTaskDtoToTask() {
        //Given
        TaskDto testTaskDto = new TaskDto(1L, "test", "test description");

        //When
        Task result = taskMapper.mapToTask(testTaskDto);

        //Then
        assertEquals(1L, result.getId(), 0);
        assertEquals("test", result.getTitle());
        assertEquals("test description", result.getContent());
    }

    @Test
    public void shouldMapTaskListToTaskDtoList() {
        //Given
        List<Task> testTaskList = new ArrayList<>();
        testTaskList.add(new Task(1L, "test", "test description"));
        testTaskList.add(new Task(2L, "test", "test description"));
        testTaskList.add(new Task(3L, "test", "test description"));

        //When
        List<TaskDto> resultList = taskMapper.mapToTaskDtoList(testTaskList);

        //Then
        assertEquals(3, resultList.size());
        resultList.forEach(taskDto -> {
            assertNotNull(taskDto.getId());
            assertEquals("test", taskDto.getTitle());
            assertEquals("test description", taskDto.getContent());
        });
    }

    @Test
    public void shouldMapEmptyTaskListToTaskList() {
        //Given
        List<Task> testTaskList = new ArrayList<>();

        //When
        List<TaskDto> resultList = taskMapper.mapToTaskDtoList(testTaskList);

        //Then
        assertNotNull(resultList);
        assertEquals(0, resultList.size());
    }
}