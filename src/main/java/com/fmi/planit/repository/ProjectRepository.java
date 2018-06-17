package com.fmi.planit.repository;


import com.fmi.planit.model.Project;
import com.fmi.planit.abstr.GenericRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ProjectRepository extends GenericRepository<Project, Long> {


}
