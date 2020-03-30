package interpreter;

public class DelimiterTable {
  public static char[] delimiter = { ';', '(', ')', '+', '-', '*', '/', '>', '<', '=', '\"', '\'',
		  '$', '%', '@', '#', '&', '^', '-', '!' };
  public static char[] enabled_delimiter = { ';', '(', ')', '+', '-', '*', '/', '>', '<', '=', '\"', '\'' };
  
  public static boolean is_delimiter( char character ) {
    for ( int i = 0 ; i < delimiter.length ; i++ ) {
      if ( delimiter[i] == character )
        return true;
    }
    return false;
  }
  
  public static boolean is_enabled_Delimiter( char character ) {
    for (  int i = 0 ; i < delimiter.length ; i++ ) {
      if ( enabled_delimiter[i] == character )
        return true;
    }
    
    return false;
  }
  
}
