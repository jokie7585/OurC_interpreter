package interpreter;

public class DelimiterTable {
  public static char[] sDelimiter = { ';', '(', ')', '+', '-', '*', '/', '>', '<', '=', '\"', '\'', '$', '%',
      '@', '#', '&', '^', '-', '!', '.', ':' };
  public static String[] sEnabled_delimiter = { ";", "+", "-", "*", "/", ":=", ">", "<", "=", ">=", "<=",
      "<>" };
  
  public static boolean Is_delimiter( char character ) {
    for ( int i = 0 ; i < sDelimiter.length ; i++ ) {
      if ( sDelimiter[ i ] == character ) {
        return true;
      } // if
    } // for
    
    return false;
  } // Is_delimiter()
  
  public static boolean Is_enabled_DelimiterToken( String token ) {
    for ( int i = 0 ; i < sEnabled_delimiter.length ; i++ ) {
      if ( sEnabled_delimiter[ i ].equals( token ) ) {
        return true;
      } // if
    } // for
    
    return false;
  } // Is_enabled_Delimiter()
  
} // class DelimiterTable
