package com.fmi.planit.service;

import com.fmi.planit.abstr.AbstractService;
import com.fmi.planit.abstr.GenericRepository;
import com.fmi.planit.model.Folder;
import com.fmi.planit.repository.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class FolderService extends AbstractService<Folder, Long> {


    @Autowired
    private FolderRepository repository;

    @Override
    public GenericRepository<Folder, Long> getDao() {
        return repository;
    }

    public List<Folder> getAllFoldersForProject(Long projectId) {
        return repository.getAllFoldersForProject(projectId);
    }
}
