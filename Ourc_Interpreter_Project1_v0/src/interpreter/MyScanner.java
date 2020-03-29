package interpreter;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;

/**
 * 
 * @author tsaipengying
 *
 */

public class MyScanner {
  
  private static MyScanner singleTone_MyScanner;
  private Vector<AlineOfToken> tokenStream = new Vector<AlineOfToken>();
  private Scanner scanner = new Scanner( System.in );
  private int currentPointerTo_tokenStream = 0;
  private int basePointerTo_tokenStream = 0;
  private int currentLine = 0;
  
  /**
   * Lock constructor.
   */
  private MyScanner() {
    
  }
  
  /**
   * 
   * @return
   */
  public static MyScanner getMyScanner() {
    if ( singleTone_MyScanner == null )
      singleTone_MyScanner = new MyScanner();
    return singleTone_MyScanner;
  }
  
  public Scanner getsscanner() {
    return scanner;
  }
  
  /**
   * return the token point by "currentPointer" in TokenStream. And move
   * "currentPointer" to next token in Stream.
   */
  public String get_NextToken() throws Throwable {
    // 先取出目前的line
    AlineOfToken currentLine = tokenStream.elementAt( this.currentLine );
    if ( currentPointerTo_tokenStream < currentLine.size() ) {
      return currentLine.elementAt( currentPointerTo_tokenStream++ ).get();
    }
    
    return null;
  }
  
  /**
   * return the token point by "currentPointer" in TokenStream.
   */
  public Token peek_NextToken() throws Throwable {
    AlineOfToken currentLine = tokenStream.elementAt( this.currentLine );
    if ( currentPointerTo_tokenStream < currentLine.size() ) {
      return currentLine.elementAt( currentPointerTo_tokenStream );
    }
    else {
      if ( updateLine() ) {
        currentLine = tokenStream.elementAt( this.currentLine );
        return currentLine.elementAt( currentPointerTo_tokenStream );
      }
    }
    return null;
  }
  
  public void skipLine() {
    updateLine();
  }
  
  public boolean updateLine() {
    currentLine++;
    while ( currentLine < tokenStream.size() ) {
      
      if ( tokenStream.elementAt( currentLine ).size() > 0 ) {
        currentPointerTo_tokenStream = 0;
        basePointerTo_tokenStream = 0;
        return true;
      }
      currentLine++;
      
    }
    
    return false;
    
  }
  
  /**
   * When Parser find an executable command, parser call this function to update
   * TokenStream's base(the index of the first unmatched token).
   */
  public void comfirmedACommand() throws Throwable {
    basePointerTo_tokenStream = currentPointerTo_tokenStream;
  }
  
  /**
   * 當parser遇到無法繼續parse的情況，可以reset tokenStream 到上一次匹配到可執行指令之前的狀態。
   */
  public void setTokenStream_to_Base() throws Throwable {
    currentPointerTo_tokenStream = basePointerTo_tokenStream;
  }
  
  /**
   * 從system.in讀入input, 並切成token製作成Token類存好。
   * 
   * @throws Throwable
   */
  public void getInputFromStream() throws Throwable {
    
    try {
      while ( scanner.hasNext() ) {
        tokenStream.add( new AlineOfToken() );
        StringProcessor stringProcessor = new StringProcessor( scanner.nextLine() );
        while ( stringProcessor.hasToken() ) {
          tokenStream.elementAt( currentLine ).add( new Token( stringProcessor.getNextToken() ) );
        }
        currentLine++;
        
      }
      // 初始化 currentLine
      currentLine = 0;
      
    } catch ( NoSuchElementException e ) {
      System.out.println( "dev_MyScanner.getInputFromStream() throws an exception!" );
      return;
    }
  }
  
  /**
   * 清空scanner的緩存。
   */
  public void flushStorage() throws Throwable {
    tokenStream = new Vector<AlineOfToken>();
  }
  
  /**
   * printAll tokens in token stream with ',' as delimiter
   * 
   * @throws Throwable
   */
  public void _dev_printAll() throws Throwable {
    for ( int j = 0 ; j < tokenStream.size() ; j++ ) {
      AlineOfToken currentAline = tokenStream.elementAt( j );
      StringBuffer temp = new StringBuffer();
      temp.append( "line " + j + " : " );
      temp.append( "[" );
      for ( int i = 0 ; i < currentAline.size() ; i++ ) {
        temp.append( currentAline.elementAt( i ).get() + ", " );
      }
      temp.delete( temp.length() - 2, temp.length() );
      temp.append( " ]" );
      System.out.println( temp.toString() );
    }
    
  }
  
  private class AlineOfToken {
    Vector<Token> alineOfTokens = new Vector<Token>();
    
    public int size() {
      return alineOfTokens.size();
    }
    
    private Token elementAt( int index ) {
      return alineOfTokens.elementAt( index );
    }
    
    private void add( Token token ) {
      alineOfTokens.add( token );
    }
  }
  
}
