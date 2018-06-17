package com.fmi.planit.controller;


import com.fmi.planit.mapping.CategoryMapping;
import com.fmi.planit.mapping.ProjectMapping;
import com.fmi.planit.mapping.UserProjectMapping;
import com.fmi.planit.model.*;
import com.fmi.planit.repository.UserRepository;
import com.fmi.planit.repository.UsersProjectsRepository;
import com.fmi.planit.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping(value="/projects")
public class ProjectController {

    @Autowired
    private ProjectService service;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ListTableService listService;

    @Autowired
    private FileService fileService;

    @Autowired
    private UsersProjectsService usersProjectsService;

    @ResponseBody
    @RequestMapping(value = "/selectAll", method = RequestMethod.GET)
    public Map<String, Object> selectAll(){
        Map<String, Object> map = new HashMap<>();
        map.put("projects",service.selectAll() );
        map.put("status", HttpStatus.ACCEPTED);
        return map;
    }


    @ResponseBody
    @RequestMapping(value = "/selectProjects", method = RequestMethod.GET)
    public Map<String,Object> selectProjects(@RequestParam("id") String id){
        Map<String, Object> map = new HashMap<>();

        //select all for user but return a mappping with project and favourites
        User user = userService.selectId(Long.parseLong(id));
        List<UserProject> userProjects = userService.selectAllProjectsByUser(user.getId());
        ArrayList<ProjectMapping> projectMappings = new ArrayList<>();
        for (int i = 0; i < userProjects.size(); i++){
            ProjectMapping pm = new ProjectMapping();
            Project project = service.selectId(userProjects.get(i).getProjectId());
            pm.setProject(project);
            pm.setAdmin(userProjects.get(i).getAdmin());
            projectMappings.add(pm);
        }
        map.put("projects",projectMappings);
        map.put("status",HttpStatus.ACCEPTED);

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Map<String,Object> add(@RequestBody UserProjectMapping userProjectMapping){
        Map<String, Object> map = new HashMap<>();
        Project project = new Project();
        project.setDescription(userProjectMapping.getDescription());
        project.setName(userProjectMapping.getName());
        project.setStartDate(userProjectMapping.getStartDate());
        project.setFinishDate(userProjectMapping.getFinishDate());
        project = service.getDao().save(project);
        UserProject up = new UserProject();
        up.setAdmin(true);
        up.setProjectId(project.getId());
        up.setUserId(Long.parseLong(userProjectMapping.getUser()));
        usersProjectsService.add(up);


        map.put("project",project);
        map.put("status",HttpStatus.ACCEPTED);

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/getCategories", method = RequestMethod.GET)
    public Map<String,Object> getCategories(@RequestParam String id) {
        Map<String,Object> map = new HashMap<>();
        Long projectId = Long.parseLong(id);

        List<Task> tasks = taskService.getAllTasksForAProject(projectId);
        List<ListTable> lists = listService.getAllListsForAProject(projectId);
        List<User> users = userService.getAllUsersForAProject(projectId);
        List<File> files = fileService.getAllFilesForAProject(projectId);
        ArrayList<CategoryMapping> categories = new ArrayList<>();
        if(users.size() == 1)
            categories.add( new CategoryMapping("User",users.size()));
        else
            categories.add( new CategoryMapping("Users",users.size()));

        if(lists.size() == 1)
            categories.add( new CategoryMapping("List",lists.size()));
        else
            categories.add( new CategoryMapping("Lists",lists.size()));

        if(files.size() == 1)
            categories.add( new CategoryMapping("File",files.size()));
        else
            categories.add( new CategoryMapping("Files",files.size()));

        if(tasks.size() == 1)
            categories.add( new CategoryMapping("Task",tasks.size()));
        else
            categories.add( new CategoryMapping("Tasks",tasks.size()));

        //get the number for all tasks, lists, users, files
        map.put("categories", categories);
        map.put("status", HttpStatus.ACCEPTED);

        return map;
    }


}
