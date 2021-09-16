package edu.tum.ase.compiler.unit;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import edu.tum.ase.compiler.model.CompilationResult;
import edu.tum.ase.compiler.model.SourceCode;
import edu.tum.ase.compiler.service.CompilerService;
import edu.tum.ase.compiler.service.OperatingSystemService;
import edu.tum.ase.compiler.service.OperatingSystemService.ProcessResult;


@SpringBootTest
public class CompilerServiceTests {
	@Autowired
	private CompilerService systemUnderTest;
	
	@MockBean
	private OperatingSystemService osService;
	
	@Test
	public void should_ThrowException_When_PassedIllegalFileType() throws Exception {
		// given
		SourceCode sourceCode = new SourceCode("", "test.html");
		
		// when
		try {
			systemUnderTest.compile(sourceCode);			
		} catch(IllegalArgumentException e) {
			return;
		}
		
		//then
		fail("An exception should have been thrown");
	}
	
	@Test
	public void should_ReturnCorrectStdoutAndStderr_WhenCompiling() throws IOException {
		// given
		SourceCode sourceCode = new SourceCode("", "test.c");
		String stdout = "stdout";
		String stderr = "stderr";
		
		given(osService.execute(ArgumentMatchers.anyString()))
		.willReturn(new ProcessResult(stdout, stderr, 1));
				
		// when
		try {
			CompilationResult result = systemUnderTest.compile(sourceCode);
			
			// then
			then(result.getStderr()).isEqualTo(stderr);
			then(result.getStdout()).isEqualTo(stdout);
			
		} catch (Exception e) {
			fail("Compilation should not throw an exception");
		}
	}
	
	@Test
	public void should_ExecuteCCompiler_When_PassedCPPFile() throws Exception {
		// given
		SourceCode sourceCode = new SourceCode("", "test.c");
		given(osService.execute(ArgumentMatchers.anyString())).willReturn(new ProcessResult("", "", 0));
		
		// when
		systemUnderTest.compile(sourceCode);
		
		// then
		verify(osService).execute(ArgumentMatchers.startsWith("gcc"));
	}
	
	@Test
	public void should_ExecuteJavaCompiler_When_PassedJavaFile() throws Exception {
		// given
		SourceCode sourceCode = new SourceCode("", "test.java");
		given(osService.execute(ArgumentMatchers.anyString())).willReturn(new ProcessResult("", "", 0));
		
		// when
		systemUnderTest.compile(sourceCode);
		
		// then
		verify(osService).execute(ArgumentMatchers.startsWith("javac"));
	}
	
	@Test
	public void should_ThrowException_When_FilenameIsEmpty() throws Exception  {
		// given
		SourceCode sourceCode = new SourceCode();
		
		// when
		try {
			systemUnderTest.compile(sourceCode);			
		} catch(IllegalArgumentException e) {
			return;
		}
		
		fail("Exception should have been thrown");
	}
}
