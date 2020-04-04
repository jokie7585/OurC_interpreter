package interpreter;

public class SegmenticErrorException extends Exception {
  private static final long serialVersionUID = 1L;
  String mErrorMassage;
  
  public SegmenticErrorException() {
    mErrorMassage = "Error";
  } // SegmenticErrorException()
  
  public SegmenticErrorException( String token ) {
    mErrorMassage = "Undefined identifier : '" + token + "'";
  } // SegmenticErrorException()
  
  public String ToString() {
    return mErrorMassage;
  } // ToString()
} // class SegmenticErrorException
