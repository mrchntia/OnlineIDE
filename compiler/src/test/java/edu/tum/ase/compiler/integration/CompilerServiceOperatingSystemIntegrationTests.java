package edu.tum.ase.compiler.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.tum.ase.compiler.model.CompilationResult;
import edu.tum.ase.compiler.model.SourceCode;
import edu.tum.ase.compiler.service.CompilerService;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
public class CompilerServiceOperatingSystemIntegrationTests {
	@Autowired
	private CompilerService compilerService;
	
	@Test
	public void should_NotFail_When_PassedTwoDifferentCodes() throws Exception {
		// given
		SourceCode sourceCode1 = new SourceCode("int main(){}", "test.c");
		SourceCode sourceCode2 = new SourceCode("class Test{}", "test.c");
		
		// when
		CompilationResult result1 = compilerService.compile(sourceCode1);
		CompilationResult result2 = compilerService.compile(sourceCode2);
		
		// then
		then(result1.isCompilable()).isEqualTo(true);
		then(result2.isCompilable()).isEqualTo(false);
	}
	
	@Test
	public void should_WorkRepeatedly_When_PassedSameCode() throws Exception {
		// given
		SourceCode sourceCode = new SourceCode("int main() {}", "test.c");
		for(int i = 0; i< 10; i++) {
			// when
			CompilationResult result = compilerService.compile(sourceCode);
			
			// then
			then(result.isCompilable()).isEqualTo(true);
		}
	}
	
	@Test
	public void should_FailRepeatedly_When_PassedSameBadCode() throws Exception {
		// given
		SourceCode sourceCode = new SourceCode("x", "test.java");
		for(int i = 0; i< 10; i++) {
			// when
			CompilationResult result = compilerService.compile(sourceCode);
			
			// then
			then(result.isCompilable()).isEqualTo(false);
		}
	}
	
	@Test
	public void should_DefendAgainstInjection_When_PassedShellCodeAsFilename() {
		// given
		String filename = "test.c | echo Test1234 | echo .c";
		SourceCode sourceCode = new SourceCode("int main(){}", filename);
		
		// when
		CompilationResult result;
		try {
			result = compilerService.compile(sourceCode);			
		} catch(Exception e) {
			return;
		}
		
		// then
		then(result.getStdout()).doesNotContain("Test1234");
	}
}
