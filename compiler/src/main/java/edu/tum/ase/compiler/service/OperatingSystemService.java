package edu.tum.ase.compiler.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

/**
 * Wrapper for all interactions with the operating system. Makes mocking the OS a lot easier.
 */
@Service
public class OperatingSystemService {
	public static class ProcessResult {
		public final String stdout;
		public final String stderr;
		public final int exitCode;
		
		public ProcessResult(String stdout, String stderr, int exitCode) {
			this.stdout = stdout;
			this.stderr = stderr;
			this.exitCode = exitCode;
		}
	}
	
	public void makeDirectory(File directory) {
		directory.mkdir();
	}
	
	public void emptyDirectory(File directory) {
		Stream.of(directory.listFiles()).forEach(File::delete);
	}
	
	public boolean deleteDirectory(File directory) {
		return directory.delete();
	}
	
	public void writeToFile(File filename, String content) throws IOException {
		FileWriter writer = new FileWriter(filename);
		writer.write(content);
		writer.close();
	}
	
	public ProcessResult execute(String command) throws IOException {
		Process process = Runtime.getRuntime().exec(command);
		while(true) {
			try {
				process.waitFor();
				break;
			} catch (InterruptedException e) {}
		}
		
		return new ProcessResult(
				IOUtils.toString(process.getInputStream(), Charset.defaultCharset()), 
				IOUtils.toString(process.getErrorStream(), Charset.defaultCharset()), 
				process.exitValue());
	}
}
