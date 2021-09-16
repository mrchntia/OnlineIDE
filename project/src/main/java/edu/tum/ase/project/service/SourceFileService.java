package edu.tum.ase.project.service;

import edu.tum.ase.project.model.Project;
import edu.tum.ase.project.model.SourceFile;
import edu.tum.ase.project.repository.SourceFileRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SourceFileService {
    @Autowired
    private SourceFileRepository sourceFileRepository;


    @PreAuthorize("hasRole('ROLE_USER')")
    public SourceFile createFile(SourceFile sourceFile){
        return sourceFileRepository.save(sourceFile);
    }

    @PreAuthorize("@sourceFileRepository.findById(#sourceFileId).get().project.isAllowed(principal)==true")
    public SourceFile findById(String sourceFileId) {
        return sourceFileRepository
                .findById(sourceFileId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid projectId:" + sourceFileId));
    }

    @PreAuthorize("#project.isAllowed(principal)==true")
    public List<SourceFile> getSourceFiles(Project project){
        return sourceFileRepository.findByProject(project);
    }

    @PostFilter("filterObject.project.isAllowed(principal)==true")
    public List<SourceFile> getAll(){
        return sourceFileRepository.findAll();
    }

    @PreAuthorize("#sourceFile.project.isAllowed(principal)  ==true")
    public void deleteFile(SourceFile sourceFile) {
        sourceFileRepository.delete(sourceFile);
    }

    @PreAuthorize("@sourceFileRepository.findById(#sourceFileId).get().project.isAllowed(principal)==true")
    public SourceFile updateSourceFileName(String sourceFileId, String name) {
        SourceFile sourceFile = sourceFileRepository
                .findById(sourceFileId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sourceFileId:" + sourceFileId));
        sourceFile.setName(name);
        sourceFileRepository.save(sourceFile);
        return sourceFile;
    }

    @PreAuthorize("@sourceFileRepository.findById(#sourceFileId).get().project.isAllowed(principal)==true")
    public SourceFile updateSourceFileCode(String sourceFileId, String sourceCode) {
        SourceFile sourceFile = sourceFileRepository
                .findById(sourceFileId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sourceFileId:" + sourceFileId));
        sourceFile.setSourceCode(sourceCode);
        sourceFileRepository.save(sourceFile);
        return sourceFile;
    }
}
