package com.fmi.planit.repository;

import com.fmi.planit.abstr.GenericRepository;
import com.fmi.planit.model.File;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface FileRepository extends GenericRepository<File, Long> {

    @Query(value = "select * from files fi join folders f on f.id = fi.id_folder where f.id_project = :projectId", nativeQuery = true)
    List<File> getAllFilesForAProject(@Param("projectId") Long projectId);
}
