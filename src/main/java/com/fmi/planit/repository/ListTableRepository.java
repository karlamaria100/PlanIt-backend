package com.fmi.planit.repository;

import com.fmi.planit.abstr.GenericRepository;
import com.fmi.planit.model.ListTable;
import com.fmi.planit.model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ListTableRepository extends GenericRepository<ListTable, Long> {

    @Query(value = "select * from lists where id_project = :projectId", nativeQuery = true)
    List<ListTable> getAllListsForAProject(@Param("projectId") Long projectId);

}
