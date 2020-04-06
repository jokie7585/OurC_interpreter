package interpreter;

public class SegmenticErrorException extends Exception {
  
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
