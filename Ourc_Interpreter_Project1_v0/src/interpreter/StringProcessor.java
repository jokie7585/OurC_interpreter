package interpreter;

/**
 * the StringProcessor pares the String to "tokens" which Interpreter need.
 * 
 * @author tsaipengying
 *
 */

public class StringProcessor {
  
  // The buffer stored a line of command
  
  private StringBuffer mInsBuffer; // a Line of command
  
  // constructor to build a String processor to parse a line to tokens by
  // giving a String
  
  public StringProcessor( String instruction ) throws Throwable {
    mInsBuffer = new StringBuffer( instruction );
    DeleteComment();
  } // StringProcessor()
  
  public String GetNextToken() throws Throwable {
    
    StringBuffer tokenBuffer = new StringBuffer();
    
    while ( mInsBuffer.length() > 0 ) {
      SkipWhiteSpace();
      if ( Is_digit( mInsBuffer.charAt( 0 ) ) || mInsBuffer.charAt( 0 ) == '.' ) {
        NumMatcher( mInsBuffer, tokenBuffer );
      } // if
      else if ( Is_letter( mInsBuffer.charAt( 0 ) ) ) {
        IdentMatcher( mInsBuffer, tokenBuffer );
      } // else if
      else {
        // delimiter
        DelimiterMatcher( mInsBuffer, tokenBuffer );
      } // else
      
      return tokenBuffer.toString();
    } // while
    
    throw new EndOfInputException();
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
  
  public static boolean Is_Num( StringBuffer buffer ) {
    for ( int i = 0 ; i < buffer.length() ; i++ ) {
      if ( !Is_digit( buffer.charAt( i ) ) ) {
        return false;
      } // if
    } // for
    
    return true;
  } // Is_Num()
  
  public static boolean Is_digit( char character ) {
    if ( character >= 48 && character <= 57 ) {
      return true;
    } // if
    
    return false;
  } // Is_digit()
  
  private static boolean Is_letter( char character ) {
    if ( ( character >= 65 && character <= 90 ) || ( character >= 97 && character <= 122 ) ) {
      return true;
    } // if
    
    return false;
  } // Is_letter()
  
  private static void IdentMatcher( StringBuffer insBuffer, StringBuffer identMatchermatcherBuffer ) {
    
    identMatchermatcherBuffer.append( insBuffer.charAt( 0 ) );
    insBuffer.deleteCharAt( 0 );
    while ( insBuffer.length() > 0 ) {
      if ( Is_letter( insBuffer.charAt( 0 ) ) || Is_digit( insBuffer.charAt( 0 ) )
          || insBuffer.charAt( 0 ) == '_' ) {
        identMatchermatcherBuffer.append( insBuffer.charAt( 0 ) );
        insBuffer.deleteCharAt( 0 );
      } // if
      else {
        return;
      } // else
    } // while
    
  } // IdentMatcher()
  
  private static void NumMatcher( StringBuffer insBuffer, StringBuffer numMatchermatcherBuffer ) {
    boolean dotFind = false;
    
    while ( insBuffer.length() > 0 ) {
      if ( Is_digit( insBuffer.charAt( 0 ) ) || insBuffer.charAt( 0 ) == '.' ) {
        if ( !dotFind ) {
          if ( insBuffer.charAt( 0 ) == '.' ) {
            dotFind = true;
          } // if
          
          numMatchermatcherBuffer.append( insBuffer.charAt( 0 ) );
          insBuffer.deleteCharAt( 0 );
        } // if
        else {
          if ( insBuffer.charAt( 0 ) == '.' ) {
            return;
          } // if
          else {
            numMatchermatcherBuffer.append( insBuffer.charAt( 0 ) );
            insBuffer.deleteCharAt( 0 );
          } // else
        } // else
      } // if
      else {
        return;
      } // else
    } // while
    
  } // NumMatcher()
  
  private static void DelimiterMatcher( StringBuffer insBuffer, StringBuffer delimitermatcherBuffer ) {
    delimitermatcherBuffer.append( insBuffer.charAt( 0 ) );
    insBuffer.deleteCharAt( 0 );
    if ( delimitermatcherBuffer.toString().equals( ":" ) && insBuffer.length() > 0 ) {
      if ( insBuffer.charAt( 0 ) == '=' ) {
        delimitermatcherBuffer.append( insBuffer.charAt( 0 ) );
        insBuffer.deleteCharAt( 0 );
      } // if
    } // if
    else if ( delimitermatcherBuffer.toString().equals( "<" ) && insBuffer.length() > 0 ) {
      if ( insBuffer.charAt( 0 ) == '=' || insBuffer.charAt( 0 ) == '>' ) {
        delimitermatcherBuffer.append( insBuffer.charAt( 0 ) );
        insBuffer.deleteCharAt( 0 );
      } // if
    } // else if
    else if ( delimitermatcherBuffer.toString().equals( ">" ) && insBuffer.length() > 0 ) {
      if ( insBuffer.charAt( 0 ) == '=' ) {
        delimitermatcherBuffer.append( insBuffer.charAt( 0 ) );
        insBuffer.deleteCharAt( 0 );
      } // if
    } // else if
    
    return;
  } // DelimiterMatcher()
  
} // class StringProcessor
