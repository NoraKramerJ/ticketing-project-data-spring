package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
private final UserService userservice;
private final UserMapper userMapper;
private final TaskService taskService;
    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserService userservice, UserMapper userMapper, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userservice = userservice;
        this.userMapper = userMapper;
        this.taskService = taskService;
    }

    @Override
    public ProjectDTO getByProjectCode(String code) {

     Project project=   projectRepository.findByProjectCode(code);
   return  projectMapper.convertToDto(project);

    }

    @Override
    public List<ProjectDTO> listAllProject() {

        List<Project> ProjectList=projectRepository.findAll(Sort.by("projectCode"));
        return ProjectList.stream().map(projectMapper::convertToDto).collect(Collectors.toList());

    }

    @Override
    public void save(ProjectDTO dto) {
        dto.setProjectStatus(Status.OPEN);
       projectRepository.save(projectMapper.convertToEntity(dto));
    }

    @Override
    public void update(ProjectDTO dto) {
        Project project = projectRepository.findByProjectCode(dto.getProjectCode());// has id
        // Map update user dto to entity object
        Project convertedProject = projectMapper.convertToEntity(dto); // no id

        // set id to the converted object
        convertedProject.setId(project.getId());

        convertedProject.setProjectStatus(project.getProjectStatus());
        // save the updated user in the db
        projectRepository.save(convertedProject);
    }

    @Override
    public void delete(String code) {
        Project project= projectRepository.findByProjectCode(code);
        project.setDeleted(true);

        //This line of coding is used so the deleted project code can be reused
        // since we will change the code of the project before deleting it
        project.setProjectCode(project.getProjectCode() + "-"+ project.getId()); //   SPOO-1
        projectRepository.save(project);
        taskService.deleteByProject(projectMapper.convertToDto(project));
    }

    @Override
    public void complete(String projectCode) {

        Project project=projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);

        taskService.completeByProject(projectMapper.convertToDto(project));
    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {

        UserDTO currentUserDTO = userservice.findByUserName("harold@manager.com");
        User user = userMapper.convertToEntity(currentUserDTO);


        //hey DB,give me all projects assigned to manager login in the system
        List<Project> list = projectRepository.findProjectByAssignedManager(user);
        return list.stream().map(project -> {

            ProjectDTO obj = projectMapper.convertToDto(project);
            obj.setUnfinishedTaskCounts(taskService.totalNonCompletedTask(project.getProjectCode()));
            obj.setCompleteTaskCounts(taskService.totalCompletedTask(project.getProjectCode()));
            return obj;
        }).collect(Collectors.toList());
    }
}
