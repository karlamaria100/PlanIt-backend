package com.fmi.planit.repository;

import com.fmi.planit.abstr.GenericRepository;
import com.fmi.planit.model.Folder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface FolderRepository extends GenericRepository<Folder, Long> {

    @Query(value = "select * from folders where id_project = :projectId", nativeQuery = true)
    List<Folder> getAllFoldersForProject(@Param("projectId") Long projectId);
}
