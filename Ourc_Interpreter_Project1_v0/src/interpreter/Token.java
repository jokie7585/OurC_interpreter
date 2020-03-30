package interpreter;

/**
 * this class used to store a "token with metadata"
 * 
 * @author tsaipengying
 */
public class Token {
  
  private String token;
  private Terminal_symbol typeOfSymbol;
  
  public Token( String token ) throws Throwable {
    this.token = token;
    try {
      tokenChecker();
    } catch ( LexicalErrorException e ) {
      // TODO: handle exception
      System.out.println( "asdasd" );
    }
  } // Token()
  
  public String get() throws Throwable {
    return token;
  } // get()
  
  public Terminal_symbol symbolOf() {
    return typeOfSymbol;
  } // typeOf()
  
  private void tokenChecker() throws Throwable {
    if ( is_IDENT() ) {
      typeOfSymbol = Terminal_symbol.IDENT;
    }
    else if ( is_NUM() ) {
      typeOfSymbol = Terminal_symbol.NUM;
    }
    else if ( DelimiterTable.is_enabled_Delimiter( token.charAt( 0 ) ) ) {
      typeOfSymbol = Terminal_symbol.Delimiter;
    }
    else {
      throw new LexicalErrorException();
    }
  } // tokenChecker()
  
  private boolean is_IDENT() throws Throwable {
    int i = 0;
    if ( is_letter( token.charAt( i ) ) ) {
      // first char must be a letter
      while ( i < token.length() ) {
        if ( is_digit( token.charAt( i ) ) || is_letter( token.charAt( i ) ) || token.charAt( i ) == '_' ) {
          i++;
        }
        else {
          return false;
        }
      }
    }
    else {
      return false;
    }
    
    return true;
  } // is_IDENT()
  
  private boolean is_NUM() throws Throwable {
    boolean dotFind = false;
    int i = 0;
    while ( i < token.length() ) {
      if ( !dotFind ) {
        if ( token.charAt( i ) == '.' ) {
          dotFind = true;
          i++;
        }
        else if ( is_digit( token.charAt( i ) ) ) {
          i++;
        }
        else {
          return false;
        }
      }
      else {
        // "dot" is found, if another dot or sth occur, than it is not a "NUM".
        if ( is_digit( token.charAt( i ) ) ) {
          i++;
        }
        else {
          return false;
        }
      }
    }
    return true;
  } // is_NUM()
  
  private boolean is_letter( char character ) throws Throwable {
    if ( ( character >= 65 && character <= 90 ) || ( character >= 97 && character <= 122 ) )
      return true;
    return false;
  } // is_letter()
  
  private boolean is_digit( char character ) throws Throwable {
    if ( character >= 48 && character <= 57 )
      return true;
    return false;
  } // is_digit()
  
} // class Token
