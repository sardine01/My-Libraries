package sar.html.exception;

public class NoTagException extends Exception {

	public NoTagException() {
		super();
	}

	public NoTagException(String msg) {
		super(msg);
	}

	public NoTagException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NoTagException(Throwable cause) {
		super(cause);
	}

	public void printStackTrace(){
		System.out.println(this.getClass().getName() + " : Tag name is null. Element cannot be created without a tag.");
		super.printStackTrace();
	}
}
