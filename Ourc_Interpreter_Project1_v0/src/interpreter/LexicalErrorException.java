package interpreter;

/**
 * this exception(error) occurs when scanner find a Lexical Error.
 * 
 * @author tsaipengying
 *
 */
public class LexicalErrorException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * @Override
	 */
	public String toString() {
		return "there is an fucking lexical ERROR!";
	}

}
