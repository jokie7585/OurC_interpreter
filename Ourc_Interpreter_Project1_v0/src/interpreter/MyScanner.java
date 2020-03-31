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
  
  private static MyScanner sSingleTone_MyScanner;
  private Vector<AlineOfToken> mTokenStream = new Vector<AlineOfToken>();
  private Scanner mScanner = new Scanner( System.in );
  private int mCurrentPointerTo_tokenStream = 0;
  private int mBasePointerTo_tokenStream = 0;
  private int mCurrentLine = 0;
  
  /**
   * Lock constructor
   */
  
  private MyScanner() {
    
  } // MyScanner()
  
  public static MyScanner GetMyScanner() {
    if ( sSingleTone_MyScanner == null ) {
      sSingleTone_MyScanner = new MyScanner();
    } // if
    
    return sSingleTone_MyScanner;
  } // GetMyScanner()
  
  public Scanner Getsscanner() {
    return mScanner;
  } // Getsscanner()
  
  /**
   * return the token point by "currentPointer" in TokenStream. And move
   * "currentPointer" to next token in Stream
   */
  public String Get_NextToken() throws Throwable {
    // 先取出目前的line
    AlineOfToken currentLine = mTokenStream.elementAt( this.mCurrentLine );
    if ( mCurrentPointerTo_tokenStream < currentLine.Size() ) {
      return currentLine.ElementAt( mCurrentPointerTo_tokenStream++ ).Get();
    } // if
    
    return null;
  } // Get_NextToken()
  
  /**
   * return the token point by "currentPointer" in TokenStream
   */
  public Token Peek_NextToken() throws Throwable {
    AlineOfToken currentLine = mTokenStream.elementAt( this.mCurrentLine );
    if ( mCurrentPointerTo_tokenStream < currentLine.Size() ) {
      return currentLine.ElementAt( mCurrentPointerTo_tokenStream );
    } // if
    else {
      if ( UpdateLine() ) {
        currentLine = mTokenStream.elementAt( this.mCurrentLine );
        return currentLine.ElementAt( mCurrentPointerTo_tokenStream );
      } // if
    } // else
    
    return null;
  } // Peek_NextToken()
  
  public void SkipLine() {
    UpdateLine();
  } // SkipLine()
  
  public boolean UpdateLine() {
    mCurrentLine++;
    while ( mCurrentLine < mTokenStream.size() ) {
      
      if ( mTokenStream.elementAt( mCurrentLine ).Size() > 0 ) {
        mCurrentPointerTo_tokenStream = 0;
        mBasePointerTo_tokenStream = 0;
        return true;
      } // if
      mCurrentLine++;
      
    } // while
    
    return false;
    
  } // UpdateLine()
  
  /**
   * When Parser find an executable command, parser call this function to update
   * TokenStream's base(the index of the first unmatched token)
   */
  public void ComfirmedACommand() throws Throwable {
    mBasePointerTo_tokenStream = mCurrentPointerTo_tokenStream;
  } // ComfirmedACommand()
  
  public void SetTokenStream_to_Base() throws Throwable {
    mCurrentPointerTo_tokenStream = mBasePointerTo_tokenStream;
  } // SetTokenStream_to_Base()
  
  public void GetInputFromStream() throws Throwable {
    
    try {
      while ( mScanner.hasNext() ) {
        mTokenStream.add( new AlineOfToken() );
        StringProcessor stringProcessor = new StringProcessor( mScanner.nextLine() );
        while ( stringProcessor.HasToken() ) {
          mTokenStream.elementAt( mCurrentLine ).Add( new Token( stringProcessor.GetNextToken() ) );
        } // while
        mCurrentLine++;
        
      } // while
      // 初始化 currentLine
      mCurrentLine = 0;
      
    } // try
    catch ( NoSuchElementException e ) {
      System.out.println( "dev_MyScanner.getInputFromStream() throws an exception!" );
      return;
    } // catch
  } // GetInputFromStream()
  
  /**
   * init token stream
   */
  public void FlushStream() throws Throwable {
    mTokenStream = new Vector<AlineOfToken>();
  } // FlushStream()
  
  /**
   * printAll tokens in token stream line by line
   */
  public void PrintAll() throws Throwable {
    for ( int j = 0 ; j < mTokenStream.size() ; j++ ) {
      AlineOfToken currentAline = mTokenStream.elementAt( j );
      StringBuffer temp = new StringBuffer();
      temp.append( "line " + j + " : " );
      temp.append( "[" );
      for ( int i = 0 ; i < currentAline.Size() ; i++ ) {
        temp.append( currentAline.ElementAt( i ).Get() + ", " );
      } // for
      
      temp.delete( temp.length() - 2, temp.length() );
      temp.append( " ]" );
      System.out.println( temp.toString() );
    } // for
    
  } // PrintAll()
  
} // class MyScanner

class AlineOfToken {
  Vector<Token> mAlineOfTokens = new Vector<Token>();
  
  public int Size() {
    return mAlineOfTokens.size();
  } // Size()
  
  public Token ElementAt( int index ) {
    return mAlineOfTokens.elementAt( index );
  } // ElementAt()
  
  public void Add( Token token ) {
    mAlineOfTokens.add( token );
  } // Add()
  
} // class AlineOfToken
