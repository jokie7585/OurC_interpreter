package interpreter;

/**
 * the StringProcessor pares the String to "tokens" which Interpreter need.
 * 
 * @author tsaipengying
 *
 */

public class StringProcessor {
  /**
   * The buffer stored a line of command
   */
  private StringBuffer insBuffer; // a Line of command
  
  /**
   * <pre>
   * constructor to build a String processor to parse a line to tokens by giving a String.
   * </pre>
   * 
   * @param instruction
   *          : a line of command
   *          
   */
  public StringProcessor( String instruction ) throws Throwable {
    insBuffer = new StringBuffer( instruction );
    deleteComment();
  }
  
  public String getNextToken() throws Throwable {
    
    StringBuffer tokenBuffer = new StringBuffer();
    skipWhiteSpace();
    while ( insBuffer.length() > 0 ) {
      
      if ( !DelimiterTable.is_delimiter( insBuffer.charAt( 0 ) ) && !is_whiteSpace( insBuffer.charAt( 0 ) ) ) {
        tokenBuffer.append( insBuffer.charAt( 0 ) );
        insBuffer.delete( 0, 1 );
        if ( insBuffer.length() == 0 )
          return tokenBuffer.toString();
      }
      else {
        if ( tokenBuffer.length() == 0 ) {
          if ( !is_whiteSpace( insBuffer.charAt( 0 ) ) ) {
            // delimiter found
            tokenBuffer.append( insBuffer.charAt( 0 ) );
            insBuffer.delete( 0, 1 );
            return tokenBuffer.toString();
          }
        }
        else {
          return tokenBuffer.toString();
        }
      }
    }
    
    return null;
  }
  
  public boolean hasToken() throws Throwable {
    skipWhiteSpace();
    if ( insBuffer.length() > 0 ) {
      return true;
    }
    return false;
  }
  
  public String getBuffer() throws Throwable {
    return insBuffer.toString();
  }
  
  public void skipWhiteSpace() throws Throwable {
    if ( insBuffer.length() > 0 ) {
      while ( is_whiteSpace( insBuffer.charAt( 0 ) ) ) {
        insBuffer.delete( 0, 1 );
        if ( insBuffer.length() == 0 )
          return;
      }
    }
  }
  
  public void deleteComment() throws Throwable {
    int i = 0, j = 1;
    while ( j < insBuffer.length() ) {
      if ( insBuffer.charAt( i ) == '/' && insBuffer.charAt( j ) == '/' ) {
        insBuffer.delete( i, insBuffer.length() );
      }
      i++;
      j++;
    }
    
  }
  
  public static boolean is_whiteSpace( char character ) throws Throwable {
    char[] whiteSpace = { ' ', '\t', '\n' };
    for ( char c : whiteSpace ) {
      if ( character == c )
        return true;
    }
    return false;
  }
  
}
