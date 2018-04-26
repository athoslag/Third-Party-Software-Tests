package exception;

/**
* @author Joao Vitor de Camargo
*/
public class InvalidCodeException extends Exception {

	private static final long serialVersionUID = 4012824597585892625L;
	
	public InvalidCodeException() {
		super("Invalid code");
	}


}
