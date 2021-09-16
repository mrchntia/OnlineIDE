package edu.tum.ase.compiler.controller;

import edu.tum.ase.compiler.model.CompilationResult;
import edu.tum.ase.compiler.model.SourceCode;
import edu.tum.ase.compiler.service.CompilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CompilerController {
    @Autowired
    private CompilerService compilerService;

    @RequestMapping(path = "/compile", method = RequestMethod.POST)
    public CompilationResult compile (@RequestBody SourceCode sourceCode) throws Exception {
        try {
            return compilerService.compile(sourceCode);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
