package edu.tum.ase.compiler.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import edu.tum.ase.compiler.model.SourceCode;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class CompilerE2ERestTests {
	private final String URL = "/compile";
	
	@Autowired 
	private MockMvc systemUnderTest;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void should_ReturnCompilationResult_When_GivenCode() throws Exception {
		// given
		SourceCode sourceCode = new SourceCode("int main(){}", "test.c");
		
		// when
		ResultActions result = systemUnderTest.perform(post(URL)
				.content(objectMapper.writeValueAsString(sourceCode))
				.contentType(MediaType.APPLICATION_JSON));
		
		// then
		result
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.compilable").value("true"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.stderr").value(""));
	}
	
	@Test
	public void should_ReturnCompilationResult_When_GivenBadCode() throws Exception {
		// given
		SourceCode sourceCode = new SourceCode("x", "test.java");
		
		// when
		ResultActions result = systemUnderTest.perform(post(URL)
				.content(objectMapper.writeValueAsString(sourceCode))
				.contentType(MediaType.APPLICATION_JSON));
		
		// then
		result
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.compilable").value("false"));
	}
}
