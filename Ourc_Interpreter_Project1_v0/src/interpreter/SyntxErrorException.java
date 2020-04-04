package interpreter;

public class SyntxErrorException extends Exception {
  
  private static final long serialVersionUID = 1L;
  String mTokenString;
  
  public SyntxErrorException() {
  } // SyntxErrorException()
  
  public SyntxErrorException( String token ) {
    mTokenString = token;
  } // SyntxErrorException()
  
  public String ToString() {
    return "Unexpected token : '" + mTokenString + "'";
  } // ToString()
  
} // class SyntxErrorException
