package edu.tum.ase.compiler.unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import edu.tum.ase.compiler.controller.CompilerController;
import edu.tum.ase.compiler.model.CompilationResult;
import edu.tum.ase.compiler.model.SourceCode;
import edu.tum.ase.compiler.service.CompilerService;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class CompilerControllerTests {
	@Autowired
	CompilerController systemUnderTest;
	
	@MockBean
	CompilerService compilerService;
	
	@Test
	public void should_ReturnCorrectResult_WhenCalled() throws Exception {
		// given
		SourceCode sourceCode = new SourceCode();
		CompilationResult expected = new CompilationResult("Test", "Test2", false);
		given(compilerService.compile(sourceCode)).willReturn(expected);
		
		// when
		CompilationResult actual = compilerService.compile(sourceCode);
		
		// then
		then(actual).isEqualTo(expected);
	}
}
