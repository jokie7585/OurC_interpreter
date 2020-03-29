package interpreter;

public class DelimiterTable {
  public static char[] delimiter = { ';', '(', ')', '+', '-', '*', '/', '>', '<', '=', '\"', '\'', '$', '%', '@', '#',
      '&', '^', '-', '!' };
  public static char[] enabled_delimiter = { ';', '(', ')', '+', '-', '*', '/', '>', '<', '=', '\"', '\'' };
  
  public static boolean is_delimiter( char character ) {
    for ( char c : delimiter ) {
      if ( c == character )
        return true;
    }
    return false;
  }
  
  public static boolean is_enabled_Delimiter( char character ) {
    for ( char element : enabled_delimiter ) {
      if ( element == character )
        return true;
    }
    
    return false;
  }
  
}
