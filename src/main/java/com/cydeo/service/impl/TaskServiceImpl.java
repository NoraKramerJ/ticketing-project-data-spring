package com.cydeo.service.impl;

import com.cydeo.dto.TaskDTO;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskMapper taskMapper, TaskRepository taskRepository) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskDTO> listAllTasks() {
        return null;
    }

    @Override
    public void save(TaskDTO dto) {

    }

    @Override
    public TaskDTO update(TaskDTO dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public TaskDTO findById(Long id) {
        return null;
    }
}
