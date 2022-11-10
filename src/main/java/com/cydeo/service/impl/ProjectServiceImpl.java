package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
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

    }

    @Override
    public void delete(String code) {
        Project project= projectRepository.findByProjectCode(code);
        project.setDeleted(true);
        projectRepository.save(project);
    }

    @Override
    public void complete(String projectCode) {

        Project project=projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);
    }
}
