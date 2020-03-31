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
  private StringBuffer mInsBuffer; // a Line of command
  
  /**
   * constructor to build a String processor to parse a line to tokens by giving
   * a String
   */
  public StringProcessor( String instruction ) throws Throwable {
    mInsBuffer = new StringBuffer( instruction );
    DeleteComment();
  } // StringProcessor()
  
  public String GetNextToken() throws Throwable {
    
    StringBuffer tokenBuffer = new StringBuffer();
    SkipWhiteSpace();
    while ( mInsBuffer.length() > 0 ) {
      
      if ( !DelimiterTable.Is_delimiter( mInsBuffer.charAt( 0 ) )
          && !Is_whiteSpace( mInsBuffer.charAt( 0 ) ) ) {
        tokenBuffer.append( mInsBuffer.charAt( 0 ) );
        mInsBuffer.delete( 0, 1 );
        if ( mInsBuffer.length() == 0 ) {
          return tokenBuffer.toString();
        } // if
      } // if
      else {
        if ( tokenBuffer.length() == 0 ) {
          if ( !Is_whiteSpace( mInsBuffer.charAt( 0 ) ) ) {
            // delimiter found
            tokenBuffer.append( mInsBuffer.charAt( 0 ) );
            mInsBuffer.delete( 0, 1 );
            return tokenBuffer.toString();
          } // if
        } // if
        else {
          return tokenBuffer.toString();
        } // else
      } // else
    } // while
    
    return null;
  } // GetNextToken()
  
  public boolean HasToken() throws Throwable {
    SkipWhiteSpace();
    if ( mInsBuffer.length() > 0 ) {
      return true;
    } // if
    
    return false;
  } // HasToken()
  
  public String GetBuffer() throws Throwable {
    return mInsBuffer.toString();
  } // GetBuffer()
  
  public void SkipWhiteSpace() throws Throwable {
    if ( mInsBuffer.length() > 0 ) {
      while ( Is_whiteSpace( mInsBuffer.charAt( 0 ) ) ) {
        mInsBuffer.delete( 0, 1 );
        if ( mInsBuffer.length() == 0 )
          return;
      } // while
    } // if
  } // SkipWhiteSpace()
  
  public void DeleteComment() throws Throwable {
    int i = 0, j = 1;
    while ( j < mInsBuffer.length() ) {
      if ( mInsBuffer.charAt( i ) == '/' && mInsBuffer.charAt( j ) == '/' ) {
        mInsBuffer.delete( i, mInsBuffer.length() );
      } // if
      
      i++;
      j++;
    } // while
    
  } // DeleteComment()
  
  public static boolean Is_whiteSpace( char character ) throws Throwable {
    char[] whiteSpace = { ' ', '\t', '\n' };
    for ( int i = 0 ; i < whiteSpace.length ; i++ ) {
      if ( character == whiteSpace[ i ] ) {
        return true;
      } // if
    } // for
    
    return false;
  } // Is_whiteSpace()
  
} // class StringProcessor
