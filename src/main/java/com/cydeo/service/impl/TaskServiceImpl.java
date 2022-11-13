package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.Task;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;
    private final ProjectMapper projectMapper;

    private final TaskRepository taskRepository;
    private final UserService  userService;
    private final UserMapper userMapper;

    public TaskServiceImpl(TaskMapper taskMapper, ProjectMapper projectMapper, TaskRepository taskRepository, UserService userService, UserMapper userMapper) {
        this.taskMapper = taskMapper;
        this.projectMapper = projectMapper;
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public List<TaskDTO> listAllTasks() {
        List<Task> taskList= taskRepository.findAll();

        return taskList.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
// this can also be done right away like this
// return roleRepository.findAll().stream().map(roleMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(TaskDTO dto) {
        dto.setTaskStatus(Status.OPEN);
        dto.setAssignedDate(LocalDate.now());
taskRepository.save(taskMapper.convertToEntity(dto));
    }

    @Override
    public void update(TaskDTO dto) {
        Optional<Task> task = taskRepository.findById(dto.getId());
        Task convertedTask = taskMapper.convertToEntity(dto);

        if (task.isPresent()) {
            //if task is null, get it from db, if there is a status, get the one from the UI
            convertedTask.setTaskStatus(dto.getTaskStatus()==null ? task.get().getTaskStatus() : dto.getTaskStatus());
            convertedTask.setAssignedDate(task.get().getAssignedDate());
            taskRepository.save(convertedTask);
        }
    }

    @Override
    public void delete(Long id) {

        Optional<Task> foundTask = taskRepository.findById(id);
        if (foundTask.isPresent()) {
            foundTask.get().setDeleted(true);
            taskRepository.save(foundTask.get());
        }
    }

    @Override
    public TaskDTO findById(Long id) {
       Optional<Task> task=taskRepository.findById(id);
       if(task.isPresent()){
           return taskMapper.convertToDto(task.get());
       }
       return null;
    }

    @Override
    public int totalNonCompletedTask(String projectCode) {
        return taskRepository.totalNonCompletedTask(projectCode);
    }

    @Override
    public int totalCompletedTask(String projectCode) {
        return taskRepository.totalCompletedTasks(projectCode);
    }

    @Override
    public void deleteByProject(ProjectDTO projectDTO) {
        Project project=projectMapper.convertToEntity(projectDTO);
        List<Task> tasks=taskRepository.findAllByProject(project);
        tasks.forEach(task -> delete(task.getId()));
    }

    @Override
    public void completeByProject(ProjectDTO projectDTO) {
        Project project=projectMapper.convertToEntity(projectDTO);
        List<Task> tasks=taskRepository.findAllByProject(project);
        tasks.stream().map(taskMapper::convertToDto).forEach(taskDTO -> {
            taskDTO.setTaskStatus(Status.COMPLETE);
            update(taskDTO);
        });
    }

    @Override
    public List<TaskDTO> listAllTasksByStatusIsNot(Status status) {
        UserDTO loggedInUser =userService.findByUserName("john@employee.com");
        List<Task> tasks=taskRepository.findAllByTaskStatusIsNotAndAssignedEmployee(status, userMapper.convertToEntity(loggedInUser));
        return tasks.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllTasksByStatus(Status status) {
        UserDTO loggedInUser =userService.findByUserName("john@employee.com");
        List<Task> tasks=taskRepository.findAllByTaskStatusAndAssignedEmployee(status, userMapper.convertToEntity(loggedInUser));
        return tasks.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }
}
