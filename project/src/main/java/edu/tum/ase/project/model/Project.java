package edu.tum.ase.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import java.io.Serializable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table (name = "projects")
public class Project implements Serializable {
    @Id
    @GeneratedValue (generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "project_id")
    private String id;

    @Column(name = "project_name", nullable = false, unique = true)
    private String name;

    @OneToMany (mappedBy = "project")
    @JsonIgnore
    @OnDelete(action= OnDeleteAction.CASCADE)
    private List<SourceFile> sourceFileList;

    //extend the project model to also contain users ids that can access the project
    @ElementCollection
    @CollectionTable(name= "project_project_users", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "userIds")
    private Set<String> userIds = new HashSet<>();

    public Set<String> getUserIds() {
        return userIds;
    }

    public void setUserId(String userId) {
        this.userIds.add(userId);
    }

    public Project() {
    }

    public boolean isAllowed (String user) {
        System.out.println("isAllowed() was run");
        if (this.userIds.contains(user)) {
            return true;
        } else {
            return false;
        }
    }

    public Project(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SourceFile> getSourceFileList() {
        return sourceFileList;
    }

    public void setSourceFileList(List<SourceFile> sourceFileList) {
        this.sourceFileList = sourceFileList;
    }

}
