package interpreter;

public class DelimiterTable {
  public static char[] sDelimiter = { ';', '(', ')', '+', '-', '*', '/', '>', '<', '=', '\"', '\'', '$', '%',
      '@', '#', '&', '^', '-', '!' };
  public static char[] sEnabled_delimiter = { ';', '(', ')', '+', '-', '*', '/', '>', '<', '=', '\"', '\'' };
  
  public static boolean Is_delimiter( char character ) {
    for ( int i = 0 ; i < sDelimiter.length ; i++ ) {
      if ( sDelimiter[ i ] == character ) {
        return true;
      } // if
    } // for
    
    return false;
  } // Is_delimiter()
  
  public static boolean Is_enabled_Delimiter( char character ) {
    for ( int i = 0 ; i < sDelimiter.length ; i++ ) {
      if ( sEnabled_delimiter[ i ] == character ) {
        return true;
      } // if
    } // for
    
    return false;
  } // Is_enabled_Delimiter()
  
} // class DelimiterTable
