package com.fmi.planit.repository;

import com.fmi.planit.abstr.GenericRepository;
import com.fmi.planit.model.UserProject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface UsersProjectsRepository extends GenericRepository<UserProject, Long> {


    @Query(value = "SELECT * FROM users_projects WHERE id_user = :id", nativeQuery = true)
    List<UserProject> selectAllProjectsByUser(@Param("id") Long id);


}
