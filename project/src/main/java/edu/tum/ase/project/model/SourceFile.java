package edu.tum.ase.project.model;


import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table (name = "source_files")
public class SourceFile {
    @Id
    @GeneratedValue (generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "source_file_id")
    private String id;

    @Column(name = "file_name", nullable = false)
    private String name;

    @Column(columnDefinition="TEXT", name = "source_code")
    private String sourceCode;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    protected SourceFile() {
    }

    public SourceFile(String name, String sourceCode, Project project) {
        this.name = name;
        this.sourceCode = sourceCode;
        this.project = project;
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

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
