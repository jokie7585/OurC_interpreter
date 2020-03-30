package interpreter;

/**
 * this class used to store a "token with metadata"
 * 
 * @author tsaipengying
 */
public class Token {
  
  private String mToken;
  private Terminal_symbol mTypeOfSymbol;
  
  public Token( String token ) throws Throwable {
    this.mToken = token;
    try {
      TokenChecker();
    } catch ( LexicalErrorException e ) {
      // TODO: handle exception
      System.out.println( "asdasd" );
    } // try/catch
  } // Token()
  
  public String Get() throws Throwable {
    return mToken;
  } // Get()
  
  public Terminal_symbol SymbolOf() {
    return mTypeOfSymbol;
  } // SymbolOf()
  
  private void TokenChecker() throws Throwable {
    if ( Is_IDENT() ) {
      mTypeOfSymbol = Terminal_symbol.IDENT;
    } // if
    else if ( Is_NUM() ) {
      mTypeOfSymbol = Terminal_symbol.NUM;
    } // else if
    else if ( DelimiterTable.Is_enabled_Delimiter( mToken.charAt( 0 ) ) ) {
      mTypeOfSymbol = Terminal_symbol.DELIMITER;
    } // else if
    else {
      throw new LexicalErrorException();
    } // else
  } // TokenChecker()
  
  private boolean Is_IDENT() throws Throwable {
    int i = 0;
    if ( Is_letter( mToken.charAt( i ) ) ) {
      // first char must be a letter
      while ( i < mToken.length() ) {
        if ( Is_digit( mToken.charAt( i ) ) || Is_letter( mToken.charAt( i ) )
            || mToken.charAt( i ) == '_' ) {
          i++;
        } // if
        else {
          return false;
        } // else
      } // while
    } // if
    else {
      return false;
    } // else
    
    return true;
  } // Is_IDENT()
  
  private boolean Is_NUM() throws Throwable {
    boolean dotFind = false;
    int i = 0;
    while ( i < mToken.length() ) {
      if ( !dotFind ) {
        if ( mToken.charAt( i ) == '.' ) {
          dotFind = true;
          i++;
        } // if
        else if ( Is_digit( mToken.charAt( i ) ) ) {
          i++;
        } // else if
        else {
          return false;
        } // else
      } // if
      else {
        // "dot" is found, if another dot or sth occur, than it is not a "NUM".
        if ( Is_digit( mToken.charAt( i ) ) ) {
          i++;
        } // if
        else {
          return false;
        } // else
      } // else
    } // while
    return true;
  } // Is_NUM()
  
  private boolean Is_letter( char character ) throws Throwable {
    if ( ( character >= 65 && character <= 90 ) || ( character >= 97 && character <= 122 ) ) {
      return true;
    } // if
    return false;
  } // Is_letter()
  
  private boolean Is_digit( char character ) throws Throwable {
    if ( character >= 48 && character <= 57 ) {
      return true;
    } // if
    return false;
  } // Is_digit()
  
} // class Token
