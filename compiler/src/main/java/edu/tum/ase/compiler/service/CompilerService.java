package edu.tum.ase.compiler.service;

import edu.tum.ase.compiler.model.CompilationResult;
import edu.tum.ase.compiler.model.SourceCode;
import edu.tum.ase.compiler.service.OperatingSystemService.ProcessResult;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;
import java.util.UUID;

@Service
public class CompilerService {

	@Autowired
	private OperatingSystemService osService;
	
    private final static Map<String, String> EXTENSION_TO_COMPILER_NAME = Map.of(
            "java", "javac",
            "c", "gcc"
    );

    public CompilationResult compile(SourceCode sourceCode) throws Exception {
        String fileName = sourceCode.getFileName();
        if(fileName.isEmpty()) {
        	throw new IllegalArgumentException("Filename must not be empty!");
        }
        
        String sourceCodeExtension = FilenameUtils.getExtension(fileName);

        if(!EXTENSION_TO_COMPILER_NAME.containsKey(sourceCodeExtension)) {
            throw new IllegalArgumentException("Unknown file extension!");
        }

        // Create temporary compilation directory
        String compilationDirectoryName = "compilation-" + UUID.randomUUID();
        File compilationDirectory = new File(compilationDirectoryName);
        osService.makeDirectory(compilationDirectory);

        // Create temporary source code file
        osService.writeToFile(new File(compilationDirectoryName + "/" + fileName), sourceCode.getCode());
        
        // Compile
        String compilerName = EXTENSION_TO_COMPILER_NAME.get(sourceCodeExtension);
        ProcessResult result = osService.execute(compilerName + " " + compilationDirectoryName + "/" + fileName);

        // Extract compilation results
        CompilationResult compilationResult = new CompilationResult();
        compilationResult.setStderr(result.stderr);
        compilationResult.setStdout(result.stdout);
        compilationResult.setCompilable(compilationResult.getStderr().isEmpty());

        // Delete temporary files
        osService.emptyDirectory(compilationDirectory);
        osService.deleteDirectory(compilationDirectory);
        
        return compilationResult;
    }
}
