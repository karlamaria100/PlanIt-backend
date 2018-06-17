package com.fmi.planit.service;

import com.fmi.planit.abstr.AbstractService;
import com.fmi.planit.abstr.GenericRepository;
import com.fmi.planit.model.Comment;
import com.fmi.planit.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CommentService extends AbstractService<Comment, Long> {

    @Autowired
    private CommentRepository repository;

    @Override
    public GenericRepository<Comment, Long> getDao() {
        return repository;
    }
}
