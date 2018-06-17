package com.fmi.planit.service;

import com.fmi.planit.abstr.AbstractService;
import com.fmi.planit.abstr.GenericRepository;
import com.fmi.planit.model.User;
import com.fmi.planit.model.UserProject;
import com.fmi.planit.repository.UserRepository;
import com.fmi.planit.repository.UsersProjectsRepository;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService extends AbstractService<User,Long> {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UsersProjectsRepository usersProjectsRepository;

    @Override
    public GenericRepository<User, Long> getDao() {
        return repository;
    }

    public User getUserWithEmail(String username) {
        return repository.getUserWithEmail(username);
    }

    public User getUserByFacebookId(String username) {
        return repository.getUserByFacebookId(username);
    }

    public List<UserProject>  selectAllProjectsByUser(Long id) {
        return usersProjectsRepository.selectAllProjectsByUser(id);
    }

    public List<User> getAllUsersForAProject(Long projectId) {
        return repository.getAllUsersForAProject(projectId);
    }

    public List<User> getAllUsersThatContains(String string, Long id) {
        String username = '%'+string+'%';
        return repository.getAllUsersThatContains(username, id);
    }

    public boolean userExists(User user) {
        return true;
    }
}
