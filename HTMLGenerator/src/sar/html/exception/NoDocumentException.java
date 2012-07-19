package sar.html.exception;

public class NoDocumentException extends Exception {

	public NoDocumentException() {
		super();
	}

	public NoDocumentException(String msg) {
		super(msg);
	}

	public NoDocumentException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NoDocumentException(Throwable cause) {
		super(cause);
	}

	public void printStackTrace(){
		System.out.println(this.getClass().getName() + " : An element cannot exist independently, please create a Document first.");
		super.printStackTrace();
	}
}
