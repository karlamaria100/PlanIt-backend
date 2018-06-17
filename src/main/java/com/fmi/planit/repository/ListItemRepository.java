package com.fmi.planit.repository;

import com.fmi.planit.abstr.GenericRepository;
import com.fmi.planit.model.ListItem;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ListItemRepository extends GenericRepository<ListItem, Long> {
}
