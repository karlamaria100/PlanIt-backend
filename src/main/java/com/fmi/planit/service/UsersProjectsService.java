package com.fmi.planit.service;

import com.fmi.planit.abstr.AbstractService;
import com.fmi.planit.abstr.GenericRepository;
import com.fmi.planit.model.UserProject;
import com.fmi.planit.repository.UsersProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersProjectsService extends AbstractService<UserProject, Long> {

    @Autowired
    private UsersProjectsRepository repository;

    @Override
    public GenericRepository<UserProject, Long> getDao() {
        return repository;
    }
}
