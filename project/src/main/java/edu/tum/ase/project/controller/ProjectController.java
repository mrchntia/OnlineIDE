package edu.tum.ase.project.controller;

import edu.tum.ase.project.model.Project;
import edu.tum.ase.project.model.SourceFile;
import edu.tum.ase.project.service.ProjectService;
import edu.tum.ase.project.service.SourceFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private SourceFileService sourceFileService;

    @GetMapping("/")
    public List<Project> index() {
        return projectService.getProjects();
    }

    @PostMapping(path = "/create-project")
    public Project create_project (@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @DeleteMapping(path = "/delete-project/{id}")
    public void delete_project (@PathVariable("id") String projectId) {
        Project project = projectService.findById(projectId);
        projectService.deleteProject(project);
    }

    @GetMapping("/read-project/{id}")
    public List<SourceFile> readProject (@PathVariable("id") String projectId) {
        Project project = projectService.findById(projectId);
        return sourceFileService.getSourceFiles(project);
    }

    @PostMapping("/update-project-name/{id}")
    public Project updateProjectName (@PathVariable("id") String sourceFileId, @RequestBody String name){
        return projectService.updateProjectName(sourceFileId, name);
    }
    
    //added to include userIds that project is shared with
    @PostMapping("/share-project/{id}")
    public Project shareProject (@PathVariable("id") String projectId, @RequestBody String userId){
        return projectService.shareProject(projectId, userId);
    }
}
