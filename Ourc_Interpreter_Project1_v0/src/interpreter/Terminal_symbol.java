package interpreter;

public class Terminal_symbol {
  private String mSymbolString;
  
  Terminal_symbol( String string ) {
    mSymbolString = string;
  } // Terminal_symbol()
  
  public static final Terminal_symbol sIDENT = new Terminal_symbol( "Ident" );
  public static final Terminal_symbol sNUM = new Terminal_symbol( "Num" );
  public static final Terminal_symbol sDELIMITER = new Terminal_symbol( "Delimiter" );
  
  public String TOString() {
    return mSymbolString;
  } // TOString
} // class Terminal_symbol
