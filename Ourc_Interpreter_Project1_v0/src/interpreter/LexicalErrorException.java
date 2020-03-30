package interpreter;

/**
 * this exception(error) occurs when scanner find a Lexical Error.
 * 
 * @author tsaipengying
 *
 */
public class LexicalErrorException extends Exception {
  private static final long serialVersionUID = 1L;
  String tokenString;
  
  public LexicalErrorException() {
  } // LexicalErrorException()
  
  public LexicalErrorException( String token ) {
    tokenString = token;
  } // LexicalErrorException()
  
  /**
   * @Override
   */
  public String toString() {
    return "Unrecognized token with first char : '" + tokenString + "'";
  } // toString()
  
} // class LexicalErrorException
