package com.fmi.planit.controller;

import com.fmi.planit.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping(value = "/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @ResponseBody
    @RequestMapping(value = "/getAllTasksForProject", method = RequestMethod.GET)
    public Map<String,Object> getAllTasksForProject(@RequestParam String id){
        Map<String,Object> map = new HashMap<>();
        Long projectId = Long.parseLong(id);


        map.put("tasks",service.getAllTasksForAProject(projectId));
        map.put("status",HttpStatus.ACCEPTED);
        return map;
    }

}
