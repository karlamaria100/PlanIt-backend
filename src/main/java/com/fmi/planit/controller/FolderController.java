package com.fmi.planit.controller;


import com.fmi.planit.service.FolderService;
import com.fmi.planit.service.ListTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping(value = "folders")
public class FolderController {


    @Autowired
    private FolderService service;

    @ResponseBody
    @RequestMapping(value = "/getAllFoldersForProject", method = RequestMethod.GET)
    public Map<String,Object> getAllFoldersForProject(@RequestParam String id){
        Map<String,Object> map = new HashMap<>();
        Long projectId = Long.parseLong(id);

        map.put("folders",service.getAllFoldersForProject(projectId));
        map.put("status",HttpStatus.ACCEPTED);
        return map;
    }
}
