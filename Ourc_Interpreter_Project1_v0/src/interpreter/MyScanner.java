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
    
  } // MyScanner()
  
  public static MyScanner getMyScanner() {
    if ( singleTone_MyScanner == null )
      singleTone_MyScanner = new MyScanner();
    return singleTone_MyScanner;
  } // getMyScanner()
  
  public Scanner getsscanner() {
    return scanner;
  } // getsscanner()
  
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
  } // get_NextToken()
  
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
  } // peek_NextToken()
  
  public void skipLine() {
    updateLine();
  } // skipLine()
  
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
    
  } // updateLine()
  
  /**
   * When Parser find an executable command, parser call this function to update
   * TokenStream's base(the index of the first unmatched token).
   */
  public void comfirmedACommand() throws Throwable {
    basePointerTo_tokenStream = currentPointerTo_tokenStream;
  } // comfirmedACommand()
  
  public void setTokenStream_to_Base() throws Throwable {
    currentPointerTo_tokenStream = basePointerTo_tokenStream;
  } // setTokenStream_to_Base()
  
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
  } // getInputFromStream()
  
  /**
   * init token stream
   */
  public void flushStream() throws Throwable {
    tokenStream = new Vector<AlineOfToken>();
  } // flushStream()
  
  /**
   * printAll tokens in token stream line by line
   */
  public void printAll() throws Throwable {
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
    
  } // printAll()
  
} // class MyScanner

class AlineOfToken {
  Vector<Token> alineOfTokens = new Vector<Token>();
  
  public int size() {
    return alineOfTokens.size();
  } // size()
  
  public Token elementAt( int index ) {
    return alineOfTokens.elementAt( index );
  } // elementAt()
  
  public void add( Token token ) {
    alineOfTokens.add( token );
  } // add()
} // class AlineOfToken
