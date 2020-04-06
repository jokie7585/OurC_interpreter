package interpreter;

public class SyntxErrorException extends Exception {
  
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
