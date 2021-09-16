package edu.tum.ase.project.service;


import edu.tum.ase.project.model.Project;
import edu.tum.ase.project.repository.ProjectRepository;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2RestOperations;

import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@EnableResourceServer//this make the SecurityContext available of the Gateway, together with Bean OAuth2RestTemplate one can access the GitLab Api
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private OAuth2RestOperations restTemplate;

    @PreAuthorize("hasRole('ROLE_USER')")
    public Project createProject(Project project) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.toString();
        project.setUserId(username);
        //project.setUserId("aabc");
        System.out.println("method createProject was run");
        return projectRepository.save(project);
    }

    @PreAuthorize("@projectRepository.findById(#projectId).get().isAllowed(principal)==true")
    public Project findById(String projectId) {
        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid projectId:" + projectId));
        System.out.println("method findbyId was run");
        return project;

    }

    //Vorgehen bis jetzt mit Trial and Error - evtl. .toString() auf security object anwenden ->principal bzw authentication
    // über localhost:8000/user können Authentication Details angesehen werden
    // Links die etwas helfen: https://www.baeldung.com/spring-security-prefilter-postfilter
    // https://www.baeldung.com/spring-security-expressions
    // https://www.baeldung.com/spring-security-create-new-custom-security-expression
    // https://www.baeldung.com/spring-expression-language

    @PostFilter("filterObject.isAllowed(principal)==true")
    public List<Project> getProjects() {
        System.out.println("method getProjects was run");
        return projectRepository.findAll();
    }

    @PreAuthorize("#project.isAllowed(principal)==true")
    public void deleteProject(Project project) {
        System.out.println("method deleteProject was run");
        projectRepository.delete(project);
    }

    @PreAuthorize("@projectRepository.findById(#projectId).get().isAllowed(principal)==true")
    public Project updateProjectName(String projectId, String name) {
        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid projectId:" + projectId));
        project.setName(name);
        projectRepository.save(project);
        System.out.println("method updateProjectName was run");
        return project;
    }


    //add a user to the project
    private final String endpoint = "https://gitlab.lrz.de/api/v4/users/?username=";

    @PreAuthorize("@projectRepository.findById(#projectId).get().isAllowed(principal)==true")
    public Project shareProject(String projectId, String userId) {
        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid projectId:" + projectId));

        System.out.println("token is:");
        System.out.println(restTemplate.getAccessToken());
        System.out.println("answer to gitlab api is:");
        System.out.println("request to: " + endpoint + userId);
        System.out.println(restTemplate.getForObject(endpoint + userId, String.class));

        String JSONresponse;
        JSONresponse = restTemplate.getForObject(endpoint + userId, String.class);

        //if reponse is longer than 2 characters the user exists and should be added as valid user, otherwise json repsonse is empty
        if(JSONresponse.length() > 2) {
            project.setUserId(userId);
        }
        projectRepository.save(project);
        return project;
    }

}
