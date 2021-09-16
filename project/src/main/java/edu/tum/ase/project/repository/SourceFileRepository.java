package edu.tum.ase.project.repository;

import edu.tum.ase.project.model.Project;
import edu.tum.ase.project.model.SourceFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SourceFileRepository extends JpaRepository<SourceFile, String> {
     SourceFile findByName(String name);
     List<SourceFile> findByProject(Project project);
}
