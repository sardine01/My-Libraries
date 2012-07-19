package sar.html.exception;

public class NoRootException extends Exception {

	public NoRootException() {
		super();
	}

	public NoRootException(String msg) {
		super(msg);
	}

	public NoRootException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NoRootException(Throwable cause) {
		super(cause);
	}

	public void printStackTrace(){
		System.out.println(this.getClass().getName() + " : Please create a root node first after initializing Document.");
		super.printStackTrace();
	}
}
