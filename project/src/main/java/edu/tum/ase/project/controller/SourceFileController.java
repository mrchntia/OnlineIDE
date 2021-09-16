package edu.tum.ase.project.controller;

import edu.tum.ase.project.model.SourceFile;
import edu.tum.ase.project.service.SourceFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SourceFileController {

    @Autowired
    private SourceFileService sourceFileService;

    @PostMapping(path = "/create-source-file")
    public SourceFile createSourceFile (@RequestBody SourceFile sourceFile) {
        return sourceFileService.createFile(sourceFile);
    }

    @GetMapping("/all-source-files")
    public List<SourceFile> getAllSourceFiled(){
        return sourceFileService.getAll();
    }

    @DeleteMapping("/delete-source-file/{id}")
    public void deleteSourceFile(@PathVariable("id") String sourceFileId) {
        SourceFile sourceFile = sourceFileService.findById(sourceFileId);
        sourceFileService.deleteFile(sourceFile);
    }

    @PostMapping("/update-source-file-name/{id}")
    public SourceFile updateSourceFileName (@PathVariable("id") String sourceFileId, @RequestBody String name){
        return sourceFileService.updateSourceFileName(sourceFileId, name);
    }

    @PostMapping("/update-source-file-code/{id}")
    public SourceFile updateSourceFileCode (@PathVariable("id") String sourceFileId, @RequestBody String sourceCode){
        return sourceFileService.updateSourceFileCode(sourceFileId, sourceCode);
    }
}
