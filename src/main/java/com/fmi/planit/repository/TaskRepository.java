package com.fmi.planit.repository;

import com.fmi.planit.abstr.GenericRepository;
import com.fmi.planit.model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import javax.xml.ws.WebEndpoint;
import java.util.List;

@Repository
@Transactional
public interface TaskRepository extends GenericRepository<Task, Long> {


    @Query(value = "select * from tasks where id_project = :projectId", nativeQuery = true)
    List<Task> getAllTasksForAProject(@Param("projectId") Long projectId);
}
