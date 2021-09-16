package edu.tum.ase.compiler.model;

public class CompilationResult {
    private String stdout;
    private String stderr;
    private boolean compilable = false;

    public CompilationResult() {}
    
    public CompilationResult(String stdout, String stderr, boolean compilable) {
    	this.stdout = stdout;
    	this.stderr = stderr;
    	this.compilable = compilable;
    }

    public String getStdout() {
        return stdout;
    }

    public void setStdout(String stdout) {
        this.stdout = stdout;
    }

    public String getStderr() {
        return stderr;
    }

    public void setStderr(String stderr) {
        this.stderr = stderr;
    }

    public boolean isCompilable() {
        return compilable;
    }

    public void setCompilable(boolean compilable) {
        this.compilable = compilable;
    }
    
    @Override
	public boolean equals(Object other) {
    	if(other.getClass() != this.getClass()) 
    		return false;
    	
    	CompilationResult otherCasted = (CompilationResult) other;
    	return otherCasted.compilable == this.compilable &&
    			otherCasted.stdout == this.stdout &&
    			otherCasted.stderr == this.stderr;
    }
}
