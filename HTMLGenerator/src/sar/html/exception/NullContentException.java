package sar.html.exception;

public class NullContentException extends Exception {

	public NullContentException() {
		super();
	}

	public NullContentException(String msg) {
		super(msg);
	}

	public NullContentException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NullContentException(Throwable cause) {
		super(cause);
	}

	public void printStackTrace(){
		System.out.println(this.getClass().getName() + " : Content is null. This element seems to be a singled node like \"<xxx/>\".");
		super.printStackTrace();
	}
}
