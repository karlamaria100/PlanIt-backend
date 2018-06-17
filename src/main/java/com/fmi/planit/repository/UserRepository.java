package com.fmi.planit.repository;

import com.fmi.planit.abstr.GenericRepository;
import com.fmi.planit.model.User;
import com.fmi.planit.model.UserProject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Transactional
@Repository
public interface UserRepository extends GenericRepository<User, Long> {

    @Query(value = "select u from users u where u.email = :username")
    User getUserWithEmail(@Param("username") String username);

    @Query(value = "select * from users where facebook_id = :facebookId", nativeQuery = true)
    User getUserByFacebookId(@Param("facebookId") String facebookId);

    @Query(value = "select * from users u join users_projects up on u.id = up.id_user where up.id_project = :projectId and up.is_admin = 0", nativeQuery = true)
    List<User> getAllUsersForAProject(@Param("projectId") Long projectId);

    @Query(value = "select * from users where ((name like :username) OR (surname like :username) OR (email like :username)) AND id <> :userId", nativeQuery = true)
    List<User> getAllUsersThatContains(@Param("username") String string, @Param("userId") Long id);
}
