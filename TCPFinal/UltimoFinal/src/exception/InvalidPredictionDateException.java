package exception;

/**
* @author Joao Vitor de Camargo
*/
public class InvalidPredictionDateException extends Exception {

	private static final long serialVersionUID = 1307620410562822315L;

	public InvalidPredictionDateException() {
		super("You can't change a prediction anymore.");
	}

}