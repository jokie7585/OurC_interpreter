package interpreter;

/**
 * this exception(error) occurs when scanner find a Lexical Error.
 * 
 * @author tsaipengying
 *
 */
public class LexicalErrorException extends Exception {
  private static final long serialVersionUID = 1L;
  char mTokenString;
  
  public LexicalErrorException() {
  } // LexicalErrorException()
  
  public LexicalErrorException( char token ) {
    mTokenString = token;
  } // LexicalErrorException()
  
  public String ToString() {
    return "Unrecognized token with first char : '" + mTokenString + "'";
  } // ToString()
  
} // class LexicalErrorException
