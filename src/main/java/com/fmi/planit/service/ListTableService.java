package com.fmi.planit.service;

import com.fmi.planit.abstr.AbstractService;
import com.fmi.planit.abstr.GenericRepository;
import com.fmi.planit.model.Comment;
import com.fmi.planit.model.ListTable;
import com.fmi.planit.repository.CommentRepository;
import com.fmi.planit.repository.ListTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ListTableService extends AbstractService<ListTable, Long> {


    @Autowired
    private ListTableRepository repository;

    @Override
    public GenericRepository<ListTable, Long> getDao() {
        return repository;
    }

    public List<ListTable> getAllListsForAProject(Long projectId) {
        return repository.getAllListsForAProject(projectId);
    }
}
