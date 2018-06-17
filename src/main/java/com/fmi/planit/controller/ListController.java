package com.fmi.planit.controller;


import com.fmi.planit.service.ListTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping(value = "lists")
public class ListController {

    @Autowired
    private ListTableService service;

    @ResponseBody
    @RequestMapping(value = "/getAllListsForProject", method = RequestMethod.GET)
    public Map<String,Object> getAllListsForProject(@RequestParam String id){
        Map<String,Object> map = new HashMap<>();
        Long projectId = Long.parseLong(id);

        map.put("lists",service.getAllListsForAProject(projectId));
        map.put("status",HttpStatus.ACCEPTED);
        return map;
    }
}
