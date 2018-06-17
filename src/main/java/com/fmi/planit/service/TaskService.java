package com.fmi.planit.service;

import com.fmi.planit.abstr.AbstractService;
import com.fmi.planit.abstr.GenericRepository;
import com.fmi.planit.model.Comment;
import com.fmi.planit.model.Task;
import com.fmi.planit.repository.CommentRepository;
import com.fmi.planit.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TaskService extends AbstractService<Task, Long> {


    @Autowired
    private TaskRepository repository;

    @Override
    public GenericRepository<Task, Long> getDao() {
        return repository;
    }

    public List<Task> getAllTasksForAProject(Long projectId) {
        return repository.getAllTasksForAProject(projectId);
    }
}
