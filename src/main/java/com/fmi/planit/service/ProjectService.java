package com.fmi.planit.service;

import com.fmi.planit.model.Project;
import com.fmi.planit.repository.ProjectRepository;
import com.fmi.planit.abstr.AbstractService;
import com.fmi.planit.abstr.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService extends AbstractService<Project, Long> {

    @Autowired
    private ProjectRepository repository;

    @Override
    public GenericRepository<Project, Long> getDao() {
        return repository;
    }
}
