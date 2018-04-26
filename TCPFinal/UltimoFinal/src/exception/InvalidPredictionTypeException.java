package exception;

/**
* @author Joao Vitor de Camargo
*/
public class InvalidPredictionTypeException extends Exception {

	private static final long serialVersionUID = 1904197538265887516L;

	public InvalidPredictionTypeException() {
		super("Invalid prediction type");
	}

}
