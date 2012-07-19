package sar.html.exception;

public class DuplicateRootException extends Exception {

	public DuplicateRootException() {
		super();
	}

	public DuplicateRootException(String msg) {
		super(msg);
	}

	public DuplicateRootException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public DuplicateRootException(Throwable cause) {
		super(cause);
	}

	public void printStackTrace(){
		System.out.println(this.getClass().getName() + " : Duplicate root elements were found. A document can only include one root element.");
		super.printStackTrace();
	}
	
}
