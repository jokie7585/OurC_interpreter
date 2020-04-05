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
    // try {
    // TokenChecker();
    // } catch ( LexicalErrorException e ) {
    // System.out.println( e.ToString() );
    // }
  } // Token()
  
  public String Get() throws Throwable {
    if ( mTypeOfSymbol == null ) {
      TokenChecker();
    } // if
    
    return mToken;
  } // Get()
  
  public Terminal_symbol SymbolOf() throws LexicalErrorException {
    TokenChecker();
    
    return mTypeOfSymbol;
  } // SymbolOf()
  
  public void TokenChecker() throws LexicalErrorException {
    if ( Is_IDENT() ) {
      mTypeOfSymbol = Terminal_symbol.sIDENT;
    } // if
    else if ( Is_NUM() ) {
      mTypeOfSymbol = Terminal_symbol.sNUM;
    } // else if
    else if ( DelimiterTable.Is_enabled_DelimiterToken( mToken ) ) {
      mTypeOfSymbol = Terminal_symbol.sDELIMITER;
    } // else if
    else {
      throw new LexicalErrorException( mToken.charAt( 0 ) );
    } // else
  } // TokenChecker()
  
  private boolean Is_IDENT() throws LexicalErrorException {
    int i = 0;
    if ( Is_letter( mToken.charAt( i ) ) || mToken.charAt( i ) == '_' ) {
      // first char must be a letter
      while ( i < mToken.length() ) {
        if ( Is_digit( mToken.charAt( i ) ) || Is_letter( mToken.charAt( i ) )
            || mToken.charAt( i ) == '_' ) {
          i++;
        } // if
        else {
          throw new LexicalErrorException( mToken.charAt( i ) );
        } // else
      } // while
    } // if
    else {
      return false;
    } // else
    
    return true;
  } // Is_IDENT()
  
  private boolean Is_NUM() throws LexicalErrorException {
    boolean dotFind = false;
    int i = 0;
    if ( Is_digit( mToken.charAt( i ) ) || mToken.charAt( i ) == '.' ) {
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
            throw new LexicalErrorException( mToken.charAt( i ) );
          } // else
        } // if
        else {
          // "dot" is found, if another dot or sth occur, than it is not a
          // "NUM".
          if ( Is_digit( mToken.charAt( i ) ) ) {
            i++;
          } // if
          else {
            throw new LexicalErrorException( mToken.charAt( i ) );
          } // else
        } // else
      } // while
      
      // only dot
      if ( dotFind && mToken.length() == 1 ) {
        return false;
      } // if
    } // if
    else {
      return false;
    } // else
    
    return true;
  } // Is_NUM()
  
  private boolean Is_letter( char character ) throws LexicalErrorException {
    if ( ( character >= 65 && character <= 90 ) || ( character >= 97 && character <= 122 ) ) {
      return true;
    } // if
    
    return false;
  } // Is_letter()
  
  private boolean Is_digit( char character ) throws LexicalErrorException {
    if ( character >= 48 && character <= 57 ) {
      return true;
    } // if
    
    return false;
  } // Is_digit()
  
} // class Token
