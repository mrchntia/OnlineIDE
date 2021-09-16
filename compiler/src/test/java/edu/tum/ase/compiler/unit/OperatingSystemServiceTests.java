package edu.tum.ase.compiler.unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.tum.ase.compiler.service.OperatingSystemService;
import edu.tum.ase.compiler.service.OperatingSystemService.ProcessResult;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.IOException;

@SpringBootTest
public class OperatingSystemServiceTests {
	@Autowired
	private OperatingSystemService systemUnderTest;
	
	@Test
	public void should_ExecJavac_WhenCalled() throws IOException {
		// given
		String command = "javac --version";
		
		// when
		ProcessResult result = systemUnderTest.execute(command);
		
		// then
		then(result.exitCode).isEqualTo(0);
		then(result.stderr).isEqualTo("");
	}
	
	@Test
	public void should_ExecGcc_WhenCalled() throws IOException {
		// given
		String command = "gcc --version";
		
		// when
		ProcessResult result = systemUnderTest.execute(command);
		
		// then
		then(result.exitCode).isEqualTo(0);
	}
}
