package interpreter;

public class DelimiterTable {
  public static char[] mDelimiter = { ';', '(', ')', '+', '-', '*', '/', '>', '<', '=', '\"', '\'', '$', '%',
      '@', '#', '&', '^', '-', '!' };
  public static char[] mEnabled_delimiter = { ';', '(', ')', '+', '-', '*', '/', '>', '<', '=', '\"', '\'' };
  
  public static boolean Is_delimiter( char character ) {
    for ( int i = 0 ; i < mDelimiter.length ; i++ ) {
      if ( mDelimiter[ i ] == character )
        return true;
    }
    return false;
  } // Is_delimiter()
  
  public static boolean Is_enabled_Delimiter( char character ) {
    for ( int i = 0 ; i < mDelimiter.length ; i++ ) {
      if ( mEnabled_delimiter[ i ] == character )
        return true;
    }
    
    return false;
  } // Is_enabled_Delimiter()
  
} // class DelimiterTable
