package com.cydeo.service;



import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;

import java.util.List;

public interface TaskService {


    List<TaskDTO> listAllTasks();

    void save(TaskDTO dto);


    TaskDTO update(TaskDTO dto);

    void delete(Long id);
    TaskDTO findById(Long id);

}

