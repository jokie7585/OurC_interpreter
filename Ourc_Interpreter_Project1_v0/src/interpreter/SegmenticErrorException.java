package interpreter;

public class SegmenticErrorException extends Exception {
  private static final long serialVersionUID = 1L;
  String mErrorMassage;
  
  public SegmenticErrorException() {
    mErrorMassage = "Error";
  } // LexicalErrorException()
  
  public SegmenticErrorException( String token ) {
    mErrorMassage = "Undefined identifier : '" + token + "'";
  } // LexicalErrorException()
  
  public String ToString() {
    return mErrorMassage;
  } // ToString()
}
