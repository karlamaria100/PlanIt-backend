package com.fmi.planit.service;

import com.fmi.planit.abstr.AbstractService;
import com.fmi.planit.abstr.GenericRepository;
import com.fmi.planit.model.Comment;
import com.fmi.planit.model.File;
import com.fmi.planit.repository.CommentRepository;
import com.fmi.planit.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class FileService extends AbstractService<File, Long> {


    @Autowired
    private FileRepository repository;

    @Override
    public GenericRepository<File, Long> getDao() {
        return repository;
    }

    public List<File> getAllFilesForAProject(Long projectId) {
        return  repository.getAllFilesForAProject(projectId);
    }
}
