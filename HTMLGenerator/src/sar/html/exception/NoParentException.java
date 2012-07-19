package sar.html.exception;

public class NoParentException extends Exception {

	public NoParentException() {
		super();
	}

	public NoParentException(String msg) {
		super(msg);
	}

	public NoParentException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NoParentException(Throwable cause) {
		super(cause);
	}

	public void printStackTrace(){
		System.out.println(this.getClass().getName() + " : Cannot find parent element. May be it is already root element.");
		super.printStackTrace();
	}
}
