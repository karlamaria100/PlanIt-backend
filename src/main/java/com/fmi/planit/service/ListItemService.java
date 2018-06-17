package com.fmi.planit.service;

import com.fmi.planit.abstr.AbstractService;
import com.fmi.planit.abstr.GenericRepository;
import com.fmi.planit.model.Comment;
import com.fmi.planit.model.ListItem;
import com.fmi.planit.repository.CommentRepository;
import com.fmi.planit.repository.ListItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class ListItemService extends AbstractService<ListItem, Long> {


    @Autowired
    private ListItemRepository repository;

    @Override
    public GenericRepository<ListItem, Long> getDao() {
        return repository;
    }
}
