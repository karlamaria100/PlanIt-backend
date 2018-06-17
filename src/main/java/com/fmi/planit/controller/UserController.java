package com.fmi.planit.controller;

import com.fmi.planit.mapping.ProjectMapping;
import com.fmi.planit.mapping.UserMapping;
import com.fmi.planit.model.Project;
import com.fmi.planit.model.User;
import com.fmi.planit.model.UserProject;
import com.fmi.planit.service.UserService;
import com.fmi.planit.utils.AppConfig;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @ResponseBody
    @RequestMapping(value = "/selectUserById", method = RequestMethod.GET)
    public Map<String,Object> selectUserById(@RequestParam Long id){
        Map<String,Object> map = new HashMap<>();
        map.put("user",service.selectId(id));
        map.put("status",HttpStatus.ACCEPTED);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/selectAll", method = RequestMethod.GET)
    public Map<String,Object> selectAll(){
        Map<String,Object> map = new HashMap<>();
        map.put("users",service.selectAll());
        map.put("status",HttpStatus.ACCEPTED);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Map<String,Object> add(@RequestBody(required = false) User user){
        Map<String,Object> map = new HashMap<>();

        //change logic
        //if user exist then update and return info
        //else add
        // if login with facebook and have email: save by that
        // else save facebook id
        //if login with google then save email
        //if login with linkedin then save email

        User user1 = service.getUserWithEmail(user.getEmail());
        if(user1 != null){
            //update and return
            mergeUser(user, user1);
            user.setId(user1.getId());
            service.update(user1);
//            user = service.getDao().save(user);
            map.put("user",user1);
            map.put("status",HttpStatus.ACCEPTED);
            return map;
        }

        user1 = service.getUserByFacebookId(user.getFacebookId());
        if(user1 != null){
            mergeUser(user, user1);
            user.setId(user1.getId());
            service.update(user1);
//            user = service.getDao().save(user);
            map.put("user",user1);
            map.put("status",HttpStatus.ACCEPTED);
            return map;
        }


            //add user
        service.add(user);
        map.put("user",user);
        map.put("status",HttpStatus.ACCEPTED);
        return map;
    }

    private void mergeUser(User user, User user1) {
        if(user.getEmail() == null && user1.getEmail() != null)
            user1.setEmail(user.getEmail());
        if(user.getProfileImage() == null && user1.getProfileImage() != null)
            user1.setProfileImage(user.getProfileImage());
        if(user.getName() == null && user1.getName() != null)
            user1.setName(user.getName());
        if(user.getSurname() == null && user1.getSurname() != null)
            user1.setSurname(user.getSurname());
        if(user.getUsername() == null && user1.getUsername() != null)
            user1.setUsername(user.getUsername());
    }

    @RequestMapping(value = "/loadPhoto", method = RequestMethod.GET)
    public void loadPhoto(@RequestParam("path") String imgPath, HttpServletResponse response) throws IOException {
        try {
            FileSystemResource res = new FileSystemResource( imgPath);
            IOUtils.copy(res.getInputStream(), response.getOutputStream());
        } catch (IOException ex) {
            System.out.println("File not fount: " + ex.getMessage() + " cause: " + ex.getCause());
            response.sendError(HttpStatus.NOT_FOUND.value());
        }
    }

    @RequestMapping(value = "/setProfileImage", method = RequestMethod.POST)
    public Map<String, Object> setProfileImage(@RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        try {
//            User user = service.selectId(AppConfig.getId());
            byte[] bytes = file.getBytes();
//            Path path = Paths.get(imagePath + file.getOriginalFilename());
//            Files.write(path, bytes);
//            user.setImagePath(String.valueOf(path));
            map.put("status", HttpStatus.ACCEPTED);
            return map;
        } catch (Exception e) {
            map.put("status", HttpStatus.BAD_REQUEST);
            e.printStackTrace();
            return map;
        }
    }


    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public Map<String, Object> forgotPassword(@RequestBody UserMapping userMapping) {
        Map<String, Object> json = new HashMap<>();
        try {
//            User user = service.selectByMail(userMapping.getEmail());
            // it needs to be only one token with purpose 'FORGOT PASSWORD'
            // in the database, otherwise resetPassword will not work.
//            tokenService.deletePasswordTokensForUser(user.getId());

//            Token token = tokenService.addForgotPasswordToken(user);
//            smtpMailSenderService.sendForgotPasswordMail(userMapping.getEmail(),
//                    "Reset password", "Reset password", token.getData(), "reset-password");
//            user.setPassword(null);
//            service.update(user);
            json.put("status", HttpStatus.ACCEPTED);
            return json;
        } catch (Exception e) {
            json.put("status", HttpStatus.BAD_REQUEST);
            e.printStackTrace();
            return json;
        }

    }

    @ResponseBody
    @RequestMapping(value = "/getAllUsersForProject", method = RequestMethod.GET)
    public Map<String,Object> getAllUsersForProject(@RequestParam String id){
        Map<String,Object> map = new HashMap<>();
        Long projectId = Long.parseLong(id);

        //TODO when add password - make it null

        map.put("user",service.getAllUsersForAProject(projectId));
        map.put("status",HttpStatus.ACCEPTED);
        return map;
    }


    @ResponseBody
    @RequestMapping(value = "/searchToAddToProject", method = RequestMethod.GET)
    public Map<String,Object> searchToAddToProject(@RequestParam String string, @RequestParam String id){
        Map<String,Object> map = new HashMap<>();
        Long userId = Long.parseLong(id);
        if(string == ""){
            map.put("status",HttpStatus.BAD_REQUEST);
            return map;
        }

        //TODO when add password - make it null

        map.put("users",service.getAllUsersThatContains(string, userId));
        map.put("status",HttpStatus.ACCEPTED);
        return map;
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestBody UserMapping userMapping, HttpServletResponse response) {
        Map<String, Object> json = new HashMap<>();
        HttpHeaders responseHeaders = null;
        try {
//            User user = service.selectByMailAndPassword(userMapping.getEmail(), service.encryptedPassword(userMapping.getPassword()));

//            if (user == null)
//                json.put("status", HttpStatus.NOT_FOUND);
//            else {
//                if (!user.getActive()) {
//                    json.put("status", HttpStatus.FORBIDDEN);
//                    return json;
//                }
//
//                service.login(user);
//                String token = authentificationService.createJWT(user);
//                UserInfo userInfo = service.toUserInfo(user);
//                json.put("status", HttpStatus.ACCEPTED);
//                json.put("user", userInfo);
//
//                System.out.println(token);
//                response.addHeader("tm-token", token);
//                response.addHeader("Access-Control-Expose-Headers", "tm-token");
//                response.addHeader("Access-Control-Allowed-Headers", "tm-token");
//            }
        } catch (Exception e) {
            json.put("status", HttpStatus.BAD_REQUEST);
            e.printStackTrace();
        }
        return json;
    }


}
